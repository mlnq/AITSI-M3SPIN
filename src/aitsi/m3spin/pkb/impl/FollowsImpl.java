package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.interfaces.Follows;

import java.util.List;

public class FollowsImpl implements Follows {
    @Override
    public void setFollows(TNode node1, TNode node2) {

    }

    @Override
    public TNode getFollows(TNode node) {
        return null;
    }

    @Override
    public List<TNode> getFollowsT(TNode node) {
        return null;
    }

    @Override
    public TNode getFollowedBy(TNode node) {
        return null;
    }

    @Override
    public List<TNode> getFollowedTBy(TNode n) {
        return null;
    }

    @Override
    public Boolean isFollowed(TNode node1, TNode node2) {
        return null;
    }

    @Override
    public Boolean isFollowedT(TNode node1, TNode node2) {
        return null;
    }
}
