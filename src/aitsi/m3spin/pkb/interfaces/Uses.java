package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import java.util.List;
import java.util.Set;

public interface Uses {
    void setUses(Statement stmt, Variable var);

    void setUses(Procedure proc, Variable var);

    Set<Variable> getVarsUsedByStmt(Statement stmt);

    Set<Variable> getVarsUsedByProc(Procedure proc);

    List<Statement> getStmtsUsingVar(Variable var);

    List<Procedure> getProcsUsingVar(Variable var);

    boolean isUsed(Variable var, Statement stat);

    boolean isUsed(Variable var, Procedure proc);
}
