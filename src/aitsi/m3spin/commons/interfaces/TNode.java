package aitsi.m3spin.commons.interfaces;

public interface TNode {
    TNode getLeftSibling();
    void setLeftSibling(TNode leftSibling);
    TNode getRightSibling();
    void setRightSibling(TNode rightSibling);
    TNode getParent();
    void setParent(TNode parent);
    TNode getChild();
    void setChild(TNode child);
    String getAttribute();
    void setAttribute(String attribute);
    int getStmtLine();
    void setStmtLine(int stmtLine);
}
