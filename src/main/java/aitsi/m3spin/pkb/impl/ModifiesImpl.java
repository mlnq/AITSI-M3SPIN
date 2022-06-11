package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.interfaces.Modifies;
import lombok.NonNull;

import java.util.*;

public class ModifiesImpl implements Modifies {

    HashMap<Statement, HashSet<Variable>> varsModifiedByStmt = new HashMap<>();
    HashMap<Procedure, HashSet<Variable>> varsModifiedByProc = new HashMap<>();

    @Override
    public void setModifies(Statement stmt, Variable variable) {
        if (varsModifiedByStmt.containsKey(stmt)) {
            varsModifiedByStmt.get(stmt).add(variable);
        } else {
            HashSet<Variable> varList = new HashSet<>();
            varList.add(variable);
            varsModifiedByStmt.put(stmt, varList);
        }
    }

    @Override
    public void setModifies(Procedure proc, Variable variable) {
        if (varsModifiedByProc.containsKey(proc)) {
            varsModifiedByProc.get(proc).add(variable);
        } else {
            HashSet<Variable> varList = new HashSet<>();
            varList.add(variable);
            varsModifiedByProc.put(proc, varList);
        }
    }

    @Override
    public Set<Variable> getModified(Statement stmt) {
        return varsModifiedByStmt.containsKey(stmt) ? varsModifiedByStmt.get(stmt) : Collections.emptySet();
    }

    @Override
    public Set<Variable> getModified(Procedure proc) {
        return varsModifiedByProc.containsKey(proc) ? varsModifiedByProc.get(proc) : Collections.emptySet();
    }

    @Override
    public List<Statement> getModifiesStmt(Variable variable) {
        List<Statement> statementList = new ArrayList<>();
        for (Map.Entry<Statement, HashSet<Variable>> entry : varsModifiedByStmt.entrySet()) {
            if (entry.getValue().contains(variable))
                statementList.add(entry.getKey());
        }
        return statementList;
    }

    @Override
    public List<Procedure> getModifiesProc(Variable variable) {
        List<Procedure> procedureList = new ArrayList<>();
        for (Map.Entry<Procedure, HashSet<Variable>> entry : varsModifiedByProc.entrySet()) {
            if (entry.getValue().contains(variable))
                procedureList.add(entry.getKey());
        }
        return procedureList;
    }

    @Override
    public boolean isModified(@NonNull Variable variable, @NonNull Statement stmt) {
        if (varsModifiedByStmt.containsKey(stmt))
            return varsModifiedByStmt.get(stmt).contains(variable);
        return false;
    }

    @Override
    public boolean isModified(@NonNull Variable variable, @NonNull Procedure proc) {
        if (varsModifiedByProc.containsKey(proc))
            return varsModifiedByProc.get(proc).contains(variable);
        return false;
    }
}
