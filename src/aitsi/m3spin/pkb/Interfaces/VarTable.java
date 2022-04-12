package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.interfaces.Index;
import aitsi.m3spin.commons.interfaces.STRING;

public interface VarTable {
  int insertVar(String varName);

  String getVarName(int id);

  int getVarIndex(String varName);

  int getSize();
  Boolean isIn (String varName);

}
