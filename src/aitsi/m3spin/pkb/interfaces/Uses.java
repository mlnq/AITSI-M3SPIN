package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;

import java.util.List;

public interface Uses {
    void setUses(Statement stmt, Variable variable);

    void setUses(Procedure proc, Variable variable);

    List<Variable> getUses(Statement stmt);

    List<Variable> getUses(Procedure proc);

    List<Statement> getUsesSTMT(Variable variable);

    List<Procedure> getUsesPROC(Variable variable);

    Boolean isUsed(Variable variable, Statement stat);

    Boolean isUseded(Variable variable, Procedure proc);
}
