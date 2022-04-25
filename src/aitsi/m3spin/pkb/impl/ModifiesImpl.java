package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.impl.helpers.ProcedureVariableRelation;
import aitsi.m3spin.pkb.impl.helpers.StatementVariableRelation;
import aitsi.m3spin.pkb.interfaces.Modifies;

import java.util.ArrayList;
import java.util.List;

public class ModifiesImpl implements Modifies {

    List<StatementVariableRelation> modifiedStatementList = new ArrayList<>();
    List<ProcedureVariableRelation> modifiedProcList = new ArrayList<>();

    @Override
    public void setModifies(Statement stmt, Variable var) {
        modifiedStatementList.add(new StatementVariableRelation(stmt, var));
    }

    @Override
    public void setModifies(Procedure proc, Variable var) {
        modifiedProcList.add(new ProcedureVariableRelation(proc, var));
    }

    @Override
    public List<Variable> getModified(Statement stmt) {
        List<Variable> variableList = new ArrayList<>();
        for(StatementVariableRelation mel : modifiedStatementList){
            if(mel.getStatement().getStmtLine() == stmt.getStmtLine())
                variableList.add(mel.getVariable());
        }
        return variableList;
    }

    @Override
    public List<Variable> getModified(Procedure proc) {
        List<Variable> variableList = new ArrayList<>();
        for(ProcedureVariableRelation mel : modifiedProcList){
            if(mel.getProcedure().getAttribute() == proc.getAttribute())
                variableList.add(mel.getVariable());
        }
        return variableList;
    }

    @Override
    public List<Statement> getModifiesSTMT(Variable var) {
        List<Statement> statementList = new ArrayList<>();
        for(StatementVariableRelation mel : modifiedStatementList){
            if(mel.getVariable().getId() == var.getId())
                statementList.add(mel.getStatement());
        }
        return statementList;
    }

    @Override
    public List<Procedure> getModifiesPROC(Variable var) {
        List<Procedure> procedureList = new ArrayList<>();
        for(ProcedureVariableRelation mel : modifiedProcList){
            if(mel.getVariable().getId() == var.getId())
                procedureList.add(mel.getProcedure());
        }
        return procedureList;
    }

    @Override
    public Boolean isModified(Variable var, Statement stat) {
        for(StatementVariableRelation mel : modifiedStatementList){
            if(mel.getVariable().getId() == var.getId() & mel.getStatement().getStmtLine() == stat.getStmtLine())
                return true;
        }
        return false;
    }

    @Override
    public Boolean isModified(Variable var, Procedure proc) {
        for(ProcedureVariableRelation mel : modifiedProcList){
            if(mel.getVariable().getId() == var.getId() & mel.getProcedure().getAttribute() == proc.getAttribute())
                return true;
        }
        return false;
    }
}
