package aitsi.m3spin.pkb.Interfaces;

import aitsi.m3spin.commons.ATTR;
import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.commons.LINK_TYPE;
import aitsi.m3spin.commons.interfaces.TNODE;

import java.util.List;

public interface AST {
    //: Creates a new node of type ‘et’ and returns a reference to it
    TNODE createTNode (EntityType et);
    void setRoot (TNODE node);
    void setAttr (TNODE n, ATTR attr);
    void setFirstChild (TNODE parent,TNODE child);

    void setRightSibling (TNODE l,TNODE r);
    void setLeftSibling (TNODE l,TNODE r);


    void setChildOfLink (TNODE parent,TNODE child);

    void setLink (LINK_TYPE relation, TNODE node1, TNODE node2);
    TNODE getRoot ();
    EntityType getType (TNODE node);

    ATTR getAttr (TNODE node);
    TNODE getFirstChild (TNODE p);

    TNODE getLinkedNode (LINK_TYPE link, TNODE node1);
    //todo CreateLink(LINK_TYPE link, TNODE fromNode, toNode)???????
    Boolean isLinked (LINK_TYPE link, TNODE node1, TNODE node2);

    void setParent (TNODE p,TNODE c);
    TNODE getParent (TNODE c);
    List<TNODE> getParentedBy (TNODE p);

    TNODE getParent$(TNODE c);

    List<TNODE> getParented$By (TNODE p);

    void setFollows (TNODE p,TNODE c);
    TNODE getFollows (TNODE n);

    List<TNODE> getFollows$(TNODE n);
    TNODE getFollowedBy (TNODE n);
    List<TNODE> getFollowed$By (TNODE n);


    Boolean isFollowed (TNODE n1,TNODE n2);
    Boolean isFollowed$ (TNODE n1,TNODE n2);

    Boolean isParent (TNODE p,TNODE c);
    Boolean isParent$ (TNODE p,TNODE c);
}
