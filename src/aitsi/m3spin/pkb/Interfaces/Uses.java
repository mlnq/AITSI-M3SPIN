package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.PROC;
import aitsi.m3spin.commons.STMT;
import aitsi.m3spin.commons.VAR;

import java.util.List;

public interface Uses {
    void setUses (STMT stmt, VAR var);
    void setUses (PROC proc, VAR var);

    List<VAR> getUses(STMT stmt);
    List<VAR> getUses (PROC proc);

    List<STMT> getUsesSTMT (VAR var);
    List<PROC> getUsesPROC (VAR var);

    Boolean isUsed (VAR var, STMT stat);
    Boolean isUseded (VAR var, PROC proc);
}
