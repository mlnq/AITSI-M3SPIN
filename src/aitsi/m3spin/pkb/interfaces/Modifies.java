package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;

import java.util.List;

public interface Modifies { //
    void setModifies(Statement stmt, Variable variable);

    void setModifies(Procedure proc, Variable variable);

    List<Variable> getModified(Statement stmt);

    List<Variable> getModified(Procedure proc);

    List<Statement> getModifiesSTMT(Variable variable);

    List<Procedure> getModifiesPROC(Variable variable);

    Boolean isModified(Variable variable, Statement stmt);

    Boolean isModified(Variable variable, Procedure proc);
}
