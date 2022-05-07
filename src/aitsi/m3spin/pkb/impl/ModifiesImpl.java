package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.interfaces.Modifies;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ModifiesImpl implements Modifies {

    HashMap<Statement, HashSet<Variable>> varsModifiedByStmt = new HashMap<>();
    HashMap<Procedure, HashSet<Variable>> varsModifiedByProc = new HashMap<>();

    @Override
    public void setModifies(Statement stmt, Variable var) {
        if(varsModifiedByStmt.containsKey(stmt)) {
            varsModifiedByStmt.get(stmt).add(var);
        }
        else{
            HashSet<Variable> varList = new HashSet<>();
            varList.add(var);
            varsModifiedByStmt.put(stmt,varList);
        }
    }

    @Override
    public void setModifies(Procedure proc, Variable var) {
        if(varsModifiedByProc.containsKey(proc)) {
            varsModifiedByProc.get(proc).add(var);
        }
        else{
            HashSet<Variable> varList = new HashSet<>();
            varList.add(var);
            varsModifiedByProc.put(proc,varList);
        }
    }

    @Override
    public List<Variable> getModified(Statement stmt) {
        return (List<Variable>) varsModifiedByStmt.get(stmt);
    }

    @Override
    public List<Variable> getModified(Procedure proc) {
        return (List<Variable>) varsModifiedByProc.get(proc);
    }

    @Override
    public List<Statement> getModifiesStmt(Variable var) {
        List<Statement> statementList = new ArrayList<>();
        for (Statement stmt : varsModifiedByStmt.keySet()) {
            if(varsModifiedByStmt.get(stmt).contains(var))
                statementList.add(stmt);
        }
        return statementList;
    }

    @Override
    public List<Procedure> getModifiesProc(Variable var) {
        List<Procedure> procedureList = new ArrayList<>();
        for (Procedure proc : varsModifiedByProc.keySet()) {
            if(varsModifiedByProc.get(proc).contains(var))
                procedureList.add(proc);
        }
        return procedureList;
    }

    @Override
    public boolean isModified(Variable var, Statement stat) {

        if(varsModifiedByStmt.containsKey(stat))
            return varsModifiedByStmt.get(stat).contains(var);
        return false;
    }

    @Override
    public boolean isModified(Variable var, Procedure proc) {

        if(varsModifiedByProc.containsKey(proc))
            return varsModifiedByProc.get(proc).contains(var);
        return false;
    }
}
