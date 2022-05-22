package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;

import java.util.List;
import java.util.Set;

public interface Uses {
    void setUses(Statement stmt, Variable variable);

    void setUses(Procedure proc, Variable variable);

    Set<Variable> getVarsUsedByStmt(Statement stmt);

    Set<Variable> getVarsUsedByProc(Procedure proc);

    List<Statement> getStmtsUsingVar(Variable variable);

    List<Procedure> getProcsUsingVar(Variable variable);

    boolean isUsed(Variable variable, Statement stmt);

    boolean isUsed(Variable variable, Procedure proc);
}
