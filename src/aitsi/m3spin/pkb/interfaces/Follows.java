package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface Follows {
    void setFollows(TNode node1, TNode node2);

    TNode getFollows(TNode node);

    List<TNode> getFollowsT(TNode node);

    TNode getFollowedBy(TNode node);

    List<TNode> getFollowedTBy(TNode n);

    Boolean isFollowed(TNode node1, TNode node2);

    Boolean isFollowedT(TNode node1, TNode node2);
}
