package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.*;

import java.util.List;

public interface Modifies {
    void setModifies (STMT stmt, VAR var);
    void setModifies (Procedure proc, VAR var);

    List<VAR> getModified (STMT stmt);
    List<VAR> getModified (Procedure proc);

    List<STMT> getModifiesSTMT (VAR var);
    List<Procedure> getModifiesPROC (VAR var);

    Boolean isModified (VAR var, STMT stat);
    Boolean isModified (VAR var, Procedure proc);
}
