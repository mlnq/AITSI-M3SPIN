package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.interfaces.Modifies;

import java.util.List;

public class ModifiesImpl implements Modifies {
    @Override
    public void setModifies(Statement stmt, Variable var) {

    }

    @Override
    public void setModifies(Procedure proc, Variable var) {

    }

    @Override
    public List<Variable> getModified(Statement stmt) {
        return null;
    }

    @Override
    public List<Variable> getModified(Procedure proc) {
        return null;
    }

    @Override
    public List<Statement> getModifiesStmt(Variable var) {
        return null;
    }

    @Override
    public List<Procedure> getModifiesProc(Variable var) {
        return null;
    }

    @Override
    public Boolean isModified(Variable var, Statement stmt) {
        return null;
    }

    @Override
    public Boolean isModified(Variable var, Procedure proc) {
        return null;
    }
}
