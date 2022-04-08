package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.*;

import java.util.List;

public interface Modifies {
    void setModifies (Statement stmt, VAR var);
    void setModifies (Procedure proc, VAR var);

    List<VAR> getModified (Statement stmt);
    List<VAR> getModified (Procedure proc);

    List<Statement> getModifiesSTMT (VAR var);
    List<Procedure> getModifiesPROC (VAR var);

    Boolean isModified (VAR var, Statement stat);
    Boolean isModified (VAR var, Procedure proc);
}
