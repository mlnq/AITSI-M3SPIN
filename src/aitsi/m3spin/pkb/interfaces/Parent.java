package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface Parent {

    List<TNode> getParentedBy(TNode p);

    TNode getParent(TNode c);

    TNode getParent$(TNode c);

    List<TNode> getParented$By(TNode p);

    Boolean isParent(TNode p, TNode c);

    Boolean isParent$(TNode p, TNode c);
}
