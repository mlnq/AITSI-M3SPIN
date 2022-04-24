package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Index;

public interface ProcTable {
    //9.2.1 handbook
    Index insertProc(String procName);

    String getProcName(Index index);

    Index getProcIndex(String procName);

    Integer getProcTableSize();

    Boolean isInProcTable(String procName);
}
