package aitsi.m3spin.pkb.impl;

import java.util.List;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.interfaces.Parent;

public class ParentImpl implements Parent {

    @Override
    public void setParent(Statement parent, Statement child) {

    }

    @Override
    public TNode getParent(TNode child) {
        return null;
    }

    @Override
    public List<TNode> getParentT(TNode child) {
        return null;
    }

    @Override
    public List<TNode> getParentedBy(TNode parent) {
        return null;
    }

    @Override
    public List<TNode> getParentedByT(TNode parent) {
        return null;
    }

    @Override
    public Boolean isParent(TNode parent, TNode child) {
        return null;
    }

    @Override
    public Boolean isParentT(TNode parent, TNode child) {
        return null;
    }
}
