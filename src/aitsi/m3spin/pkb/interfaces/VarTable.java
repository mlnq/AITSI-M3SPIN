package aitsi.m3spin.pkb.interfaces;

public interface VarTable {
  int insertVar(String varName);
  String getVarName(int id);
  int getVarIndex(String varName);
  int getSize();
  boolean isIn (String varName);
}
