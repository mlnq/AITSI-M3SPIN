package aitsi.m3spin.spafrontend.extractor;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.VariableImpl;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.spafrontend.parser.RelationshipsInfo;
import aitsi.m3spin.spafrontend.parser.exception.UnknownStatementType;

import java.util.List;
import java.util.Set;

public class DesignExtractor {
    private final Pkb pkb;
    private Procedure currentProcedure;

    public DesignExtractor(Pkb pkb) {
        this.pkb = pkb;
    }

    /*
     * Wzór metody fillPkb(tnode):
     * 1. insertVar(tnode) / insertProc(tnode)
     * 2. setChild(tnode, tnodeChild) / setSibling(tnode, tnodeSibling)
     * 3. Wywołaj fillPkb() dla tego dziecka/rodzeństwa
     *
     *  todo w przyszłuch iteracjach: Calls
     * */
    public void fillPkb(List<Procedure> procedures) throws UnknownStatementType {
        System.out.println("Filling PKB with data...");

        Procedure rootProc = procedures.get(0);
        rootProc = (Procedure) pkb.getAst().setRoot(rootProc);
        fillPkb(rootProc);

        Procedure currentProc;
        Procedure lastProc = rootProc;

        for (int i = 1; i < procedures.size(); i++) {
            currentProc = procedures.get(i);
            pkb.getAst().setSibling(lastProc, currentProc);
            fillPkb(currentProc);
            lastProc = currentProc;
        }

        System.out.println("Filling PKB with data completed.");
    }

    private void fillPkb(Procedure procedure) throws UnknownStatementType {
        pkb.getProcTable().insertProc(procedure.getProcName());
        StatementList stmtList = (StatementList) pkb.getAst().setChild(procedure, procedure.getStatementList());
        RelationshipsInfo relationshipsInfo = fillPkb(stmtList);

        relationshipsInfo.getModifiedVariables()
                .forEach(modifiedVar -> pkb.getModifiesInterface().setModifies(procedure, modifiedVar));

        relationshipsInfo.getUsedVariables()
                .forEach(usedVar -> pkb.getUsesInterface().setUses(procedure, usedVar));
    }

    private RelationshipsInfo fillPkb(StatementList stmtList) throws UnknownStatementType {
        Statement firstStmt = (Statement) pkb.getAst().setChild(stmtList, stmtList.getStatements().get(0));
        RelationshipsInfo stmtListRelationshipsInfo = fillPkb(firstStmt);

        Statement currentStmt = firstStmt;

        for (int i = 1; i < stmtList.getStatements().size(); i++) {
            pkb.getFollowsInterface().setFollows(currentStmt, stmtList.getStatements().get(i));
            currentStmt = (Statement) pkb.getAst().setSibling(currentStmt, stmtList.getStatements().get(i));
            stmtListRelationshipsInfo = RelationshipsInfo.merge(stmtListRelationshipsInfo, fillPkb(currentStmt));
        }

        return stmtListRelationshipsInfo;
    }

    private RelationshipsInfo fillPkb(Statement stmt) throws UnknownStatementType {
        if (EntityType.ASSIGNMENT.equals(stmt.getType())) {
            return fillPkb((Assignment) stmt);
        } else if (EntityType.WHILE.equals(stmt.getType())) {
            return fillPkb((While) stmt);
        } else if (EntityType.IF.equals(stmt.getType())) {
            return fillPkb((If) stmt);
        } else if (EntityType.CALL.equals(stmt.getType())) {
            return fillPkb((Call) stmt);
        } else {
            throw new UnknownStatementType();
        }
    }

    private RelationshipsInfo fillPkb(While whileStmt) throws UnknownStatementType {
        Variable conditionVar = (Variable) pkb.getAst().setChild(whileStmt, whileStmt.getConditionVar());
        RelationshipsInfo relationshipsInfo = fillPkb(conditionVar, whileStmt.getStmtList());

        relationshipsInfo.getModifiedVariables()
                .forEach(modifiedVar -> pkb.getModifiesInterface().setModifies(whileStmt, modifiedVar));

        pkb.getUsesInterface().setUses(whileStmt, conditionVar);
        relationshipsInfo.getUsedVariables()
                .forEach(usedVar -> pkb.getUsesInterface().setUses(whileStmt, usedVar));

        whileStmt.getStmtList().getStatements()
                .forEach(statement -> pkb.getParentInterface().setParent(whileStmt, statement));

        return relationshipsInfo;
    }

    private RelationshipsInfo fillPkb(Variable variable, StatementList stmtList) throws UnknownStatementType {
        pkb.getVarTable().insertVar(variable.getVarName());
        stmtList = (StatementList) pkb.getAst().setSibling(variable, stmtList);
        return fillPkb(stmtList);
    }

    private RelationshipsInfo fillPkb(Expression expression) {
        if (expression.getExpression() != null) {
            RelationshipsInfo relationshipsInfo = new RelationshipsInfo();
            if (expression.getFactor() instanceof Variable) {
                Variable variable = (Variable) expression.getFactor();
                pkb.getVarTable().insertVar(variable.getVarName());
                relationshipsInfo.addUsedVar(variable);
            }
            Factor factor = (Factor) pkb.getAst().setChild(expression, expression.getFactor());

            Expression nestedExpression = (Expression) pkb.getAst().setSibling(factor, expression.getExpression());
            return RelationshipsInfo.merge(relationshipsInfo, fillPkb(nestedExpression));
        } else return RelationshipsInfo.emptyInfo();
    }

    private RelationshipsInfo fillPkb(Assignment assignment) {
        VariableImpl variable = (VariableImpl) pkb.getAst().setChild(assignment, assignment.getVariable());

        RelationshipsInfo relationshipsInfo = new RelationshipsInfo();

        relationshipsInfo.addModifiedVar(variable);
        pkb.getModifiesInterface().setModifies(assignment, variable);

        RelationshipsInfo.merge(relationshipsInfo, fillPkb(variable, assignment.getExpression()));
        relationshipsInfo.getUsedVariables()
                .forEach(usedVar -> pkb.getUsesInterface().setUses(assignment, usedVar));
        return relationshipsInfo;
    }

    private RelationshipsInfo fillPkb(Variable variable, Expression expression) {
        pkb.getVarTable().insertVar(variable.getVarName());
        expression = (Expression) pkb.getAst().setSibling(variable, expression);
        return fillPkb(expression);
    }

    private RelationshipsInfo fillPkb(Call call) {
        String calledProc = (String) call.getAttribute().getValue();
        pkb.getCallsInterface().setCalls(currentProcedure, calledProc);
        return RelationshipsInfo.emptyInfo();
    }

    private RelationshipsInfo fillPkb(If ifImpl) throws UnknownStatementType {
        RelationshipsInfo relationshipsInfo = new RelationshipsInfo();
        Variable conditionVar = ifImpl.getConditionVar();
        relationshipsInfo.addUsedVar(conditionVar);

        conditionVar = (Variable) pkb.getAst().setChild(ifImpl, conditionVar);

        pkb.getUsesInterface().setUses(ifImpl, conditionVar);

        ifImpl.getThenStmtList().getStatements()
                .forEach(statement -> pkb.getParentInterface().setParent(ifImpl, statement));
        ifImpl.getElseStmtList().getStatements()
                .forEach(statement -> pkb.getParentInterface().setParent(ifImpl, statement));

        RelationshipsInfo.merge(relationshipsInfo, fillPkb(ifImpl.getConditionVar(), ifImpl.getThenStmtList(), ifImpl.getElseStmtList()));


        return relationshipsInfo;
    }

    private RelationshipsInfo fillPkb(Variable variable, StatementList thenStmtList, StatementList elseStmtList) throws UnknownStatementType {
        pkb.getVarTable().insertVar(variable.getVarName());
        thenStmtList = (StatementList) pkb.getAst().setSibling(variable, thenStmtList);
        RelationshipsInfo relationshipsInfo = fillPkb(thenStmtList);
        elseStmtList = (StatementList) pkb.getAst().setSibling(thenStmtList, elseStmtList);
        RelationshipsInfo.merge(relationshipsInfo, fillPkb(elseStmtList));
        return relationshipsInfo;
    }
}
