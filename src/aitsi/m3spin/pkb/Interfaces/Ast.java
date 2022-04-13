package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.exception.IllegalLinkTypeException;
import aitsi.m3spin.pkb.exception.IllegalNodeTypeException;

import java.util.List;

public interface Ast {
    //: Creates a new node of type ‘et’ and returns a reference to it
    TNode createTNode(EntityType et) throws IllegalNodeTypeException;

    TNode setRoot(TNode node);
    void setAttr(TNode n, String attr);
    TNode setFirstChild(TNode parent, TNode child);

    TNode getFirstChild(TNode p);

    /* TODO jaką notację przyjmujemy w stosunku do dzieci?
    TNode setSecondChild(TNode parent, TNode child);
    TNode setThirdChild(TNode parent, TNode child);
    TNode getSecondChild(TNode p);
    TNode getThirdChild(TNode p);*/

    /**
     * Sets sibling relation
     * @param left left sibling
     * @param right right sibling
     * @return right sibling
     */
    TNode setSibling(TNode left, TNode right);

//    void setChildOfLink(TNode parent, TNode child);

    /**
     * Sets relation of two nodes
     * @param relation relation type
     * @param node1 first node
     * @param node2 second node
     * @return relationType: CHILD - returns child; PARENT - returns parent; SIBLING - return right sibling;
     * @throws IllegalLinkTypeException no such relation type
     */
    TNode setLink(LinkType relation, TNode node1, TNode node2) throws IllegalLinkTypeException;

    TNode getRoot();

    EntityType getType(TNode node);

    String getAttr(TNode node);


    TNode getLinkedNode(LinkType link, TNode node1);
    //todo CreateLink(LINK_TYPE link, TNODE fromNode, toNode)???????
    Boolean isLinked(LinkType link, TNode node1, TNode node2);

    TNode setParent(TNode p, TNode c);

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
