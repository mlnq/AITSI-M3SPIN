package aitsi.m3spin.pkb.interfaces;

public interface ProcTable {
    //9.2.1 handbook
    int insertProc(String procName);

    String getProcName(int id);

    int getProcId(String procName);

    int getSize();

    boolean isIn(String procName);
}
