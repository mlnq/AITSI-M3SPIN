package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.Procedure;
import aitsi.m3spin.commons.Statement;
import aitsi.m3spin.commons.VAR;

import java.util.List;

public interface Uses {
    void setUses (Statement stmt, VAR var);
    void setUses (Procedure proc, VAR var);

    List<VAR> getUses(Statement stmt);
    List<VAR> getUses (Procedure proc);

    List<Statement> getUsesSTMT (VAR var);
    List<Procedure> getUsesPROC (VAR var);

    Boolean isUsed (VAR var, Statement stat);
    Boolean isUseded (VAR var, Procedure proc);
}
