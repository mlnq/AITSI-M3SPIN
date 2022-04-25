package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.impl.helpers.ProcedureVariableRelation;
import aitsi.m3spin.pkb.impl.helpers.StatementVariableRelation;
import aitsi.m3spin.pkb.interfaces.Uses;

import java.util.ArrayList;
import java.util.List;

public class UsesImpl implements Uses {

    List<StatementVariableRelation> usesStatementList = new ArrayList<>();
    List<ProcedureVariableRelation> usesProcedureList = new ArrayList<>();

    @Override
    public void setUses(Statement stmt, Variable var) {
        usesStatementList.add(new StatementVariableRelation(stmt, var));
    }

    @Override
    public void setUses(Procedure proc, Variable var) {
        usesProcedureList.add(new ProcedureVariableRelation(proc, var));
    }

    @Override
    public List<Variable> getUses(Statement stmt) {
        List<Variable> variableList = new ArrayList<>();
        for(StatementVariableRelation mel : usesStatementList){
            if(mel.getStatement().getStmtLine() == stmt.getStmtLine())
                variableList.add(mel.getVariable());
        }
        return variableList;
    }

    @Override
    public List<Variable> getUses(Procedure proc) {
        List<Variable> variableList = new ArrayList<>();
        for(ProcedureVariableRelation mel : usesProcedureList){
            if(mel.getProcedure().getAttribute() == proc.getAttribute())
                variableList.add(mel.getVariable());
        }
        return variableList;
    }

    @Override
    public List<Statement> getUsesStmt(Variable var) {
        List<Statement> statementList = new ArrayList<>();
        for(StatementVariableRelation mel : usesStatementList){
            if(mel.getVariable().getId() == var.getId())
                statementList.add(mel.getStatement());
        }
        return statementList;
    }

    @Override
    public List<Procedure> getUsesProc(Variable var) {
        List<Procedure> procedureList = new ArrayList<>();
        for(ProcedureVariableRelation mel : usesProcedureList){
            if(mel.getVariable().getId() == var.getId())
                procedureList.add(mel.getProcedure());
        }
        return procedureList;
    }

    @Override
    public Boolean isUsed(Variable var, Statement stat) {
        for(StatementVariableRelation mel : usesStatementList){
            if(mel.getVariable().getId() == var.getId() & mel.getStatement().getStmtLine() == stat.getStmtLine())
                return true;
        }
        return false;
    }

    @Override
    public Boolean isUsed(Variable var, Procedure proc) {
        for(ProcedureVariableRelation mel : usesProcedureList){
            if(mel.getVariable().getId() == var.getId() & mel.getProcedure().getAttribute() == proc.getAttribute())
                return true;
        }
        return false;
    }
}
