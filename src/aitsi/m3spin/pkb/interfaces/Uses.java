package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;

import java.util.List;

public interface Uses {
    void setUses(Statement stmt, Variable var);

    void setUses(Procedure proc, Variable var);

    List<Variable> getUses(Statement stmt);

    List<Variable> getUses(Procedure proc);

    List<Statement> getUsesStmt(Variable var);

    List<Procedure> getUsesProc(Variable var);

    Boolean isUsed(Variable var, Statement stat);

    Boolean isUsed(Variable var, Procedure proc);
}
