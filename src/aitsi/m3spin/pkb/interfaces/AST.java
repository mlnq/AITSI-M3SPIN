package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.Attr;
import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface AST {
    //: Creates a new node of type ‘et’ and returns a reference to it
    TNode createTNode(EntityType et);

    void setRoot(TNode node);

    void setAttr(TNode n, Attr attr);

    TNode setFirstChild(TNode parent, TNode child);

    TNode setRightSibling(TNode l, TNode r);

    void setLeftSibling(TNode l, TNode r);

    void setChildOfLink(TNode parent, TNode child);

    void setLink(LinkType relation, TNode node1, TNode node2);

    TNode getRoot();

    EntityType getType(TNode node);

    Attr getAttr(TNode node);

    TNode getFirstChild(TNode p);

    TNode getLinkedNode(LinkType link, TNode node1);
    //todo CreateLink(LINK_TYPE link, TNODE fromNode, toNode)??????? raczej nie
    Boolean isLinked(LinkType link, TNode node1, TNode node2);

    void setParent(TNode p, TNode c);

    TNode getParent(TNode c);

    List<TNode> getParentedBy(TNode p);

    TNode getParent$(TNode c);

    List<TNode> getParented$By(TNode p);

    void setFollows(TNode p, TNode c);

    TNode getFollows(TNode n);

    List<TNode> getFollows$(TNode n);

    TNode getFollowedBy(TNode n);

    List<TNode> getFollowed$By(TNode n);


    Boolean isFollowed(TNode n1, TNode n2);

    Boolean isFollowed$(TNode n1, TNode n2);

    Boolean isParent(TNode p, TNode c);

    Boolean isParent$(TNode p, TNode c);
}
