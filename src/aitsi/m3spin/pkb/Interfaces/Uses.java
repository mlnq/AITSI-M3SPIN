package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.Procedure;
import aitsi.m3spin.commons.STMT;
import aitsi.m3spin.commons.VAR;

import java.util.List;

public interface Uses {
    void setUses (STMT stmt, VAR var);
    void setUses (Procedure proc, VAR var);

    List<VAR> getUses(STMT stmt);
    List<VAR> getUses (Procedure proc);

    List<STMT> getUsesSTMT (VAR var);
    List<Procedure> getUsesPROC (VAR var);

    Boolean isUsed (VAR var, STMT stat);
    Boolean isUseded (VAR var, Procedure proc);
}
