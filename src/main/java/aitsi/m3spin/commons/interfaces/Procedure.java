package aitsi.m3spin.commons.interfaces;

public interface Procedure extends TNode {
    String getProcName();

    void setProcName(String name);

    StatementList getStatementList();
}
