package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface Follows {
    void setFollows(TNode p, TNode c);

    TNode getFollows(TNode n);

    List<TNode> getFollows$(TNode n);

    TNode getFollowedBy(TNode n);

    List<TNode> getFollowed$By(TNode n);


    Boolean isFollowed(TNode n1, TNode n2);

    Boolean isFollowed$(TNode n1, TNode n2);
}
