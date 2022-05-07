package aitsi.m3spin.pkb.interfaces;

import java.util.List;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;

public interface Parent {

    void setParent(Statement parent, Statement child);

    TNode getParent(TNode child);

    List<TNode> getParentT(TNode child);

    List<TNode> getParentedBy(TNode parent);

    List<TNode> getParentedByT(TNode parent);

    Boolean isParent(TNode parent, TNode child);

    Boolean isParentT(TNode parent, TNode child);
}
