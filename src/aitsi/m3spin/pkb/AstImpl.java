package aitsi.m3spin.pkb;

import aitsi.m3spin.commons.ATTR;
import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.commons.LINK_TYPE;
import aitsi.m3spin.commons.interfaces.TNODE;
import aitsi.m3spin.pkb.Interfaces.AST;

import java.util.List;

public class AstImpl implements AST {
    @Override
    public TNODE createTNode(EntityType et) {
        return null;
    }

    @Override
    public void setRoot(TNODE node) {

    }

    @Override
    public void setAttr(TNODE n, ATTR attr) {

    }

    @Override
    public void setFirstChild(TNODE parent, TNODE child) {

    }

    @Override
    public void setRightSibling(TNODE l, TNODE r) {

    }

    @Override
    public void setLeftSibling(TNODE l, TNODE r) {

    }

    @Override
    public void setChildOfLink(TNODE parent, TNODE child) {

    }

    @Override
    public void setLink(LINK_TYPE relation, TNODE node1, TNODE node2) {

    }

    @Override
    public TNODE getRoot() {
        return null;
    }

    @Override
    public EntityType getType(TNODE node) {
        return null;
    }

    @Override
    public ATTR getAttr(TNODE node) {
        return null;
    }

    @Override
    public TNODE getFirstChild(TNODE p) {
        return null;
    }

    @Override
    public TNODE getLinkedNode(LINK_TYPE link, TNODE node1) {
        return null;
    }

    @Override
    public Boolean isLinked(LINK_TYPE link, TNODE node1, TNODE node2) {
        return null;
    }

    @Override
    public void setParent(TNODE p, TNODE c) {

    }

    @Override
    public TNODE getParent(TNODE c) {
        return null;
    }

    @Override
    public List<TNODE> getParentedBy(TNODE p) {
        return null;
    }

    @Override
    public TNODE getParent$(TNODE c) {
        return null;
    }

    @Override
    public List<TNODE> getParented$By(TNODE p) {
        return null;
    }

    @Override
    public void setFollows(TNODE p, TNODE c) {

    }

    @Override
    public TNODE getFollows(TNODE n) {
        return null;
    }

    @Override
    public List<TNODE> getFollows$(TNODE n) {
        return null;
    }

    @Override
    public TNODE getFollowedBy(TNODE n) {
        return null;
    }

    @Override
    public List<TNODE> getFollowed$By(TNODE n) {
        return null;
    }

    @Override
    public Boolean isFollowed(TNODE n1, TNODE n2) {
        return null;
    }

    @Override
    public Boolean isFollowed$(TNODE n1, TNODE n2) {
        return null;
    }

    @Override
    public Boolean isParent(TNODE p, TNODE c) {
        return null;
    }

    @Override
    public Boolean isParent$(TNODE p, TNODE c) {
        return null;
    }
}
