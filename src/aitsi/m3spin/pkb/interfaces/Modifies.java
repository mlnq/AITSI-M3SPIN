package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import java.util.List;
import java.util.Set;

public interface Modifies {
    void setModifies(Statement stmt, Variable var);

    void setModifies(Procedure proc, Variable var);

    Set<Variable> getModified(Statement stmt);

    Set<Variable> getModified(Procedure proc);

    List<Statement> getModifiesStmt(Variable var);

    List<Procedure> getModifiesProc(Variable var);

    boolean isModified(Variable var, Statement stat);

    boolean isModified(Variable var, Procedure proc);
}
