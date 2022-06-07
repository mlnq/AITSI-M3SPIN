package aitsi.m3spin.commons.interfaces;

import aitsi.m3spin.pkb.model.AttributableNode;

public interface Procedure extends TNode, AttributableNode {
    String getProcName();

    void setProcName(String name);

    StatementList getStatementList();
}
