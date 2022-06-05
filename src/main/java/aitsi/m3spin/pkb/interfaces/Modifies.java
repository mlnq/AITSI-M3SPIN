package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;

import java.util.List;
import java.util.Set;

public interface Modifies {
    void setModifies(Statement stmt, Variable variable);

    void setModifies(Procedure proc, Variable variable);

    Set<Variable> getModified(Statement stmt);

    Set<Variable> getModified(Procedure proc);

    List<Statement> getModifiesStmt(Variable variable);

    List<Procedure> getModifiesProc(Variable variable);

    boolean isModified(Variable variable, Statement stmt);

    boolean isModified(Variable variable, Procedure proc);
}
