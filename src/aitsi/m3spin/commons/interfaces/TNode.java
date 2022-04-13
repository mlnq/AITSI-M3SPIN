package aitsi.m3spin.commons.interfaces;

import aitsi.m3spin.commons.enums.EntityType;

public interface TNode {
    TNode getLeftSibling();
    void setLeftSibling(TNode leftSibling);
    TNode getRightSibling();
    void setRightSibling(TNode rightSibling);
    TNode getParent();
    void setParent(TNode parent);

    TNode getFirstChild();
    void setFirstChild(TNode child);

    TNode getSecondChild();
    void setSecondChild(TNode child);

    TNode getThirdChild();
    void setThirdChild(TNode child);

    String getAttribute();
    void setAttribute(String attribute);
    int getStmtLine();
    void setStmtLine(int stmtLine);

    EntityType getType();
}
