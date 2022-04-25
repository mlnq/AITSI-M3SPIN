package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.interfaces.Parent;

import java.util.ArrayList;
import java.util.List;

public class ParentImpl implements Parent {

    @Override
    public List<TNode> getParentedBy(TNode p) {

        TNode n = p;
        List<TNode> childList = new ArrayList<TNode>();

        while(n.getChild() != null)
        {
            n = n.getChild();
            childList.add(n);
        }
        return childList;
    }

    @Override
    public TNode getParent(TNode c) {
        return c.getParent();
    }

    @Override
    public TNode getParent$(TNode c) {
        return null;
    }

    @Override
    public List<TNode> getParented$By(TNode p) {
        return null;
    }

    @Override
    public Boolean isParent(TNode p, TNode c) {
        return c.getParent() == p;
    }

    @Override
    public Boolean isParent$(TNode p, TNode c) {
        return null;
    }
}
