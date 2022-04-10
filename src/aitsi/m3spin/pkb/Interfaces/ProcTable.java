package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.interfaces.Index;
import aitsi.m3spin.commons.interfaces.STRING;

public interface ProcTable {
//9.2.1 handbook
Index insertVar(STRING varName);

    STRING getVarName(Index ind);

    Index getVarIndex(STRING varName);

    Integer getSize();
    Boolean isIn (STRING varName);
}
