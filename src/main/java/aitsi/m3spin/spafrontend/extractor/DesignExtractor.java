package aitsi.m3spin.spafrontend.extractor;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.VariableImpl;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.spafrontend.parser.RelationshipsInfo;
import aitsi.m3spin.spafrontend.parser.exception.UnknownStatementType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DesignExtractor {
    private final Pkb pkb;
    private final Set<Procedure> allProcedures = new HashSet<>();
    private final HashMap<Procedure, RelationshipsInfo> relInfoMap = new HashMap<>();

    public DesignExtractor(Pkb pkb) {
        this.pkb = pkb;
    }

    /*
     * Wzór metody fillPkb(tnode):
     * 1. setChild(tnode, tnodeChild) / setSibling(tnode, tnodeSibling)
     * 2. Przygotuj RelationshipsInfo
     * 3. Wywołaj fillPkb() dla tego dziecka/rodzeństwa
     * */
    public void fillPkb(List<Procedure> procedures) throws UnknownStatementType {
        System.out.println("Filling PKB with data...");

        allProcedures.addAll(procedures);

        Procedure rootProc = procedures.get(0);
        Procedure currentProcedure;
        rootProc = (Procedure) pkb.getAst().setRoot(rootProc);
        relInfoMap.put(rootProc, fillPkb(rootProc));

        Procedure lastProc = rootProc;

        for (int i = 1; i < procedures.size(); i++) {
            currentProcedure = procedures.get(i);
            pkb.getAst().setSibling(lastProc, currentProcedure);
            relInfoMap.put(currentProcedure, fillPkb(currentProcedure));
            lastProc = currentProcedure;
        }

        relInfoMap.forEach(this::setCallsUsesModifies);

        System.out.println("Filling PKB with data completed.");
    }

    private void setCallsUsesModifies(Procedure procedure, RelationshipsInfo procRelInfo) {
        RelationshipsInfo fullRelInfo = getRelInfoFromCalledProcs(procRelInfo, procedure.getProcName());

        fullRelInfo.getCalledProcedures().stream()
                .map(calledProcName -> findProcedureByName(calledProcName, procedure.getProcName()))
                .forEach(calledProc -> pkb.getCallsInterface().setCalls(procedure, calledProc));

        setMultipleUses(procedure, fullRelInfo.getUsedVariables());
        setMultipleModifies(procedure, fullRelInfo.getModifiedVariables());
    }

    private RelationshipsInfo getRelInfoFromCalledProcs(final RelationshipsInfo procRelInfo, final String callingProcName) {
        RelationshipsInfo fullRelInfo = new RelationshipsInfo(procRelInfo);
        procRelInfo.getCalledProcedures().stream()
                .map(calledProcName -> findProcedureByName(calledProcName, callingProcName))
                .forEach(procedure -> {
                    RelationshipsInfo relInfoFromCalledProcs = getRelInfoFromCalledProcs(relInfoMap.get(procedure), callingProcName);
                    fullRelInfo.addRelInfo(relInfoFromCalledProcs);
                });

        return fullRelInfo;
    }

    private void setMultipleUses(Procedure procedure, List<Variable> usedVars) {
        usedVars.forEach(variable -> pkb.getUsesInterface().setUses(procedure, variable));
    }

    private void setMultipleModifies(Procedure procedure, List<Variable> modifiedVars) {
        modifiedVars.forEach(variable -> pkb.getModifiesInterface().setModifies(procedure, variable));
    }

    private Procedure findProcedureByName(String name, String callingProcName) {
        Procedure searchedProc;
        try {
            searchedProc = allProcedures.stream()
                    .filter(procedure -> procedure.getProcName().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new NotDeclaredProcedureCallException(name, callingProcName));
        } catch (NotDeclaredProcedureCallException e) {
            throw new RuntimeException(e);
        }
        return searchedProc;
    }

    private RelationshipsInfo fillPkb(Procedure procedure) throws UnknownStatementType {
        StatementList stmtList = (StatementList) pkb.getAst().setChild(procedure, procedure.getStatementList());
        RelationshipsInfo relationshipsInfo = fillPkb(stmtList);

        relationshipsInfo.getModifiedVariables()
                .forEach(modifiedVar -> pkb.getModifiesInterface().setModifies(procedure, modifiedVar));

        relationshipsInfo.getUsedVariables()
                .forEach(usedVar -> pkb.getUsesInterface().setUses(procedure, usedVar));

        return relationshipsInfo;
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
        stmtList = (StatementList) pkb.getAst().setSibling(variable, stmtList);
        return fillPkb(stmtList);
    }

    private RelationshipsInfo fillPkb(Expression expression) {
        if (expression.getExpression() != null) {
            RelationshipsInfo relationshipsInfo = new RelationshipsInfo();
            if (expression.getFactor() instanceof Variable) {
                Variable variable = (Variable) expression.getFactor();
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

        relationshipsInfo = RelationshipsInfo.merge(relationshipsInfo, fillPkb(variable, assignment.getExpression()));

        relationshipsInfo.getUsedVariables()
                .forEach(usedVar -> pkb.getUsesInterface().setUses(assignment, usedVar));
        return relationshipsInfo;
    }

    private RelationshipsInfo fillPkb(Variable variable, Expression expression) {
        expression = (Expression) pkb.getAst().setSibling(variable, expression);
        return fillPkb(expression);
    }

    private RelationshipsInfo fillPkb(Call call) {
        String calledProcName = (String) call.getAttribute().getValue();

        RelationshipsInfo relationshipsInfo = new RelationshipsInfo();
        relationshipsInfo.addCalledProcName(calledProcName);
        return relationshipsInfo;
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

        return RelationshipsInfo.merge(relationshipsInfo, fillPkb(ifImpl.getConditionVar(), ifImpl.getThenStmtList(), ifImpl.getElseStmtList()));
    }

    private RelationshipsInfo fillPkb(Variable variable, StatementList thenStmtList, StatementList elseStmtList) throws UnknownStatementType {
        thenStmtList = (StatementList) pkb.getAst().setSibling(variable, thenStmtList);
        RelationshipsInfo thenRelInfo = fillPkb(thenStmtList);
        elseStmtList = (StatementList) pkb.getAst().setSibling(thenStmtList, elseStmtList);
        return RelationshipsInfo.merge(thenRelInfo, fillPkb(elseStmtList));
    }
}
