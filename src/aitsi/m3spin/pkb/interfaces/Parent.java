package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface Parent { // na podstawie AST
    void setParent(TNode p, TNode c);

    TNode getParent(TNode c);

    List<TNode> getParentedBy(TNode p);

    TNode getParentT(TNode c);

    List<TNode> getParentedTBy(TNode p);

    Boolean isParent(TNode p, TNode c);

    Boolean isParentT(TNode p, TNode c);
}
