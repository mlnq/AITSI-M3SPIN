package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.interfaces.Uses;
import java.util.*;

public class UsesImpl implements Uses {

    HashMap<Statement, HashSet<Variable>> varsUsedByStmt = new HashMap<>();
    HashMap<Procedure, HashSet<Variable>> varsUsedByProc = new HashMap<>();

    @Override
    public void setUses(Statement stmt, Variable var) {
        if(varsUsedByStmt.containsKey(stmt)) {
            varsUsedByStmt.get(stmt).add(var);
        }
        else{
            HashSet<Variable> varList = new HashSet<>();
            varList.add(var);
            varsUsedByStmt.put(stmt,varList);
        }
    }

    @Override
    public void setUses(Procedure proc, Variable var) {
        if(varsUsedByProc.containsKey(proc)) {
            varsUsedByProc.get(proc).add(var);
        }
        else{
            HashSet<Variable> varList = new HashSet<>();
            varList.add(var);
            varsUsedByProc.put(proc,varList);
        }
    }

    @Override
    public Set<Variable> getVarsUsedByStmt(Statement stmt) {
        return varsUsedByStmt.get(stmt);
    }

    @Override
    public Set<Variable> getVarsUsedByProc(Procedure proc) {
        return varsUsedByProc.get(proc);
    }

    @Override
    public List<Statement> getStmtsUsingVar(Variable var) {
        List<Statement> statementList = new ArrayList<>();
        for (Statement stmt : varsUsedByStmt.keySet()) {
            if(varsUsedByStmt.get(stmt).contains(var))
                statementList.add(stmt);
        }
        return statementList;
    }

    @Override
    public List<Procedure> getProcsUsingVar(Variable var) {
        List<Procedure> procedureList = new ArrayList<>();
        for (Procedure proc : varsUsedByProc.keySet()) {
            if(varsUsedByProc.get(proc).contains(var))
                procedureList.add(proc);
        }
        return procedureList;
    }

    @Override
    public boolean isUsed(Variable var, Statement stat) {

        if(varsUsedByStmt.containsKey(stat))
            return varsUsedByStmt.get(stat).contains(var);
        return false;
    }

    @Override
    public boolean isUsed(Variable var, Procedure proc) {

        if(varsUsedByProc.containsKey(proc))
            return varsUsedByProc.get(proc).contains(var);
        return false;
    }
}
