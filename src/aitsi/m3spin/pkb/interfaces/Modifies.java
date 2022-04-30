package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;

import java.util.List;

public interface Modifies {
    void setModifies(Statement stmt, Variable var);

    void setModifies(Procedure proc, Variable var);

    List<Variable> getModified(Statement stmt);

    List<Variable> getModified(Procedure proc);

    List<Statement> getModifiesStmt(Variable var);

    List<Procedure> getModifiesProc(Variable var);

    Boolean isModified(Variable var, Statement stat);

    Boolean isModified(Variable var, Procedure proc);
}
