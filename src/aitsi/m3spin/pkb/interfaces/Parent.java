package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface Parent { // na podstawie AST
    void setParent(Statement parent, Statement child);

    TNode getParent(TNode child);

    TNode getParentT(TNode child);

    List<TNode> getParentedBy(TNode parent);

    List<TNode> getParentedTBy(TNode parent);

    Boolean isParent(TNode parent, TNode child);

    Boolean isParentT(TNode parent, TNode child);
}
