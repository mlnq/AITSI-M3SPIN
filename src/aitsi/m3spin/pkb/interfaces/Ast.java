package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.exception.IllegalLinkTypeException;
import aitsi.m3spin.pkb.exception.IllegalNodeTypeException;

public interface Ast {
    //: Creates a new node of type ‘et’ and returns a reference to it
    TNode createTNode(EntityType et) throws IllegalNodeTypeException;

    void setName(TNode node, String name);

    TNode setChild(TNode parent, TNode child);

    TNode setSibling(TNode left, TNode right);

    TNode setLink(LinkType relation, TNode node1, TNode node2) throws IllegalLinkTypeException;

    TNode getRoot();

    TNode setRoot(TNode node);

    EntityType getType(TNode node);

    String getName(TNode node);

    TNode getChild(TNode p);

    TNode getLinkedNode(LinkType link, TNode node1) throws IllegalLinkTypeException;

    Boolean isLinked(LinkType link, TNode node1, TNode node2) throws IllegalLinkTypeException;

    TNode setParent(TNode parent, TNode child);
}
