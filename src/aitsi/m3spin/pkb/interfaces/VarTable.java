package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Index;

public interface VarTable {
    Index insertVar(String varName);

    String getVarName(Index index);

    Index getVarIndex(String varName);

    Integer getVarTableSize();

    Boolean isInVarTable(String varName);

}
