package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.interfaces.Follows;

import java.util.ArrayList;
import java.util.List;

public class FollowsImpl implements Follows {

    @Override
    public void setFollows(TNode l, TNode r) {
        l.setRightSibling(r);
        r.setLeftSibling(l);
    }

    @Override
    public TNode getFollows(TNode n) {
        return n.getRightSibling();
    }

    @Override
    public List<TNode> getFollows$(TNode n) {
        return null;
    }

    @Override
    public List<TNode> getFollowedBy(TNode n) {
        TNode t = n;
        List<TNode> siblingList = new ArrayList<TNode>();

        while(t.getRightSibling() != null)
        {
            t = t.getRightSibling();
            siblingList.add(t);
        }
        return siblingList;
    }

    @Override
    public List<TNode> getFollowed$By(TNode n) {
        return null;
    }

    @Override
    public Boolean isFollowed(TNode n1, TNode n2) {
        return n1.getRightSibling() == n2;
    }

    @Override
    public Boolean isFollowed$(TNode n1, TNode n2) {
        return null;
    }
}
