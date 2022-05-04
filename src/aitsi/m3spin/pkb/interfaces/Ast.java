package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.exception.IllegalLinkTypeException;
import aitsi.m3spin.pkb.exception.IllegalNodeTypeException;
import aitsi.m3spin.pkb.impl.ParentImpl;

import java.util.List;

public interface Ast {
    //: Creates a new node of type ‘et’ and returns a reference to it
    TNode createTNode(EntityType et) throws IllegalNodeTypeException;

    TNode setRoot(TNode node);
    void setAttr(TNode n, String attr);
    TNode setChild(TNode parent, TNode child);

    TNode getChild(TNode p);

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
     * Relation types:
     *  - PARENT: node1 becomes parent and node2 becomes child
     *  - CHILD: node1 becomes child and node2 becomes parent
     *  - SIBLING: node1 becomes left sibling and node2 becomes right sibling
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

    /**
     * Gets node connected by relation of given type with given node (argument)
     * @param link type of connection between nodes
     * @param node checked node
     * @return node connected by link with node
     * @throws IllegalLinkTypeException link is not a valid link type
     */
    TNode getLinkedNode(LinkType link, TNode node) throws IllegalLinkTypeException;

    /**
     * Gets information if node2 is connected with node1 by connection of link type
     * @param link type of relation
     * @param node1 first checked node
     * @param node2 second checked node
     * @throws IllegalLinkTypeException link is not a valid link type
     * @return true if nodes are connected by link or false if not
     */
    Boolean isLinked(LinkType link, TNode node1, TNode node2) throws IllegalLinkTypeException;
}
