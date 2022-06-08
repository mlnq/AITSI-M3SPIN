package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.interfaces.Uses;
import lombok.NonNull;

import java.util.*;

public class UsesImpl implements Uses {

    HashMap<Statement, HashSet<Variable>> varsUsedByStmt = new HashMap<>();
    HashMap<Procedure, HashSet<Variable>> varsUsedByProc = new HashMap<>();

    @Override
    public void setUses(Statement stmt, Variable variable) {
        if (varsUsedByStmt.containsKey(stmt)) {
            varsUsedByStmt.get(stmt).add(variable);
        } else {
            HashSet<Variable> varList = new HashSet<>();
            varList.add(variable);
            varsUsedByStmt.put(stmt, varList);
        }
    }

    @Override
    public void setUses(Procedure proc, Variable variable) {
        if (varsUsedByProc.containsKey(proc)) {
            varsUsedByProc.get(proc).add(variable);
        } else {
            HashSet<Variable> varList = new HashSet<>();
            varList.add(variable);
            varsUsedByProc.put(proc, varList);
        }
    }

    @Override
    public Set<Variable> getVarsUsedByStmt(Statement stmt) {
        return varsUsedByStmt.containsKey(stmt) ? varsUsedByStmt.get(stmt) : Collections.emptySet();
    }

    @Override
    public Set<Variable> getVarsUsedByProc(Procedure proc) {
        return varsUsedByProc.containsKey(proc) ? varsUsedByProc.get(proc) : Collections.emptySet();
    }

    @Override
    public List<Statement> getStmtsUsingVar(Variable variable) {
        List<Statement> statementList = new ArrayList<>();
        for (Map.Entry<Statement, HashSet<Variable>> entry : varsUsedByStmt.entrySet()) {
            if (entry.getValue().contains(variable))
                statementList.add(entry.getKey());
        }
        return statementList;
    }

    @Override
    public List<Procedure> getProcsUsingVar(Variable variable) {
        List<Procedure> procedureList = new ArrayList<>();
        for (Map.Entry<Procedure, HashSet<Variable>> entry : varsUsedByProc.entrySet()) {
            if (entry.getValue().contains(variable))
                procedureList.add(entry.getKey());
        }
        return procedureList;
    }

    @Override
    public boolean isUsed(@NonNull Variable variable, @NonNull Statement stmt) {
        if (varsUsedByStmt.containsKey(stmt))
            return varsUsedByStmt.get(stmt).contains(variable);
        return false;
    }

    @Override
    public boolean isUsed(@NonNull Variable variable, @NonNull Procedure proc) {
        if (varsUsedByProc.containsKey(proc))
            return varsUsedByProc.get(proc).contains(variable);
        return false;
    }
}
