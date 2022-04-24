package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface Follows { // na podstawie z CFG
    void setFollows(TNode p, TNode c);

    TNode getFollows(TNode n);

    List<TNode> getFollowsT(TNode n);

    TNode getFollowedBy(TNode n);

    List<TNode> getFollowedTBy(TNode n);

    Boolean isFollowed(TNode n1, TNode n2);

    Boolean isFollowedT(TNode n1, TNode n2);
}
