package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.INDEX;
import aitsi.m3spin.commons.STRING;

public interface ProcTable {
//9.2.1 handbook
    INDEX insertVar (STRING varName);
    STRING getVarName (INDEX ind);
    INDEX getVarIndex (STRING varName);

    Integer getSize();
    Boolean isIn (STRING varName);
}
