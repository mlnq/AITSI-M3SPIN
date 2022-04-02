package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.*;

public interface VarTable {
  INDEX insertVar (STRING varName);
  STRING getVarName (INDEX ind);
  INDEX getVarIndex (STRING varName);

  Integer getSize();
  Boolean isIn (STRING varName);

}
