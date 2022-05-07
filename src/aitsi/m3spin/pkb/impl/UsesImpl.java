package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.interfaces.Uses;

import java.util.List;

public class UsesImpl implements Uses {
    @Override
    public void setUses(Statement stmt, Variable var) {

    }

    @Override
    public void setUses(Procedure proc, Variable var) {

    }

    @Override
    public List<Variable> getUses(Statement stmt) {
        return null;
    }

    @Override
    public List<Variable> getUses(Procedure proc) {
        return null;
    }

    @Override
    public List<Statement> getUsesStmt(Variable var) {
        return null;
    }

    @Override
    public List<Procedure> getUsesProc(Variable var) {
        return null;
    }

    @Override
    public Boolean isUsed(Variable var, Statement stmt) {
        return null;
    }

    @Override
    public Boolean isUsed(Variable var, Procedure proc) {
        return null;
    }
}
