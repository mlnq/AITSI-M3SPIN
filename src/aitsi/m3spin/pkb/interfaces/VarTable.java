package aitsi.m3spin.pkb.interfaces;

public interface VarTable {
    int insertVar(String varName);

    String getVarName(int id);

    int getVarId(String varName);

    int getVarTableSize();

    Boolean isInVarTable(String varName);
}
