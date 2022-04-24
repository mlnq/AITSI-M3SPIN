package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.Attr;
import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.TNode;

public interface Ast {
    //: Creates a new node of type ‘et’ and returns a reference to it
    TNode createTNode(EntityType et);

    void setAttr(TNode n, Attr attr);

    TNode setChild(TNode parent, TNode child);

    TNode setRightSibling(TNode l, TNode r);

    void setLeftSibling(TNode l, TNode r);

    void setChildOfLink(TNode parent, TNode child);

    void setLink(LinkType relation, TNode node1, TNode node2);

    TNode getRoot();

    void setRoot(TNode node);

    EntityType getType(TNode node);

    Attr getAttr(TNode node);

    TNode getFirstChild(TNode p);

    TNode getLinkedNode(LinkType link, TNode node1);

    //todo CreateLink(LINK_TYPE link, TNODE fromNode, toNode)??????? raczej nie
    Boolean isLinked(LinkType link, TNode node1, TNode node2);
}
