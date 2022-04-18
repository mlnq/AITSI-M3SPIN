package aitsi.m3spin.pkb;

import aitsi.m3spin.commons.Attr;
import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.interfaces.AST;

import java.util.List;

public class AstImpl implements AST {
    @Override
    public TNode createTNode(EntityType et) {
        return null;
    }

    @Override
    public void setRoot(TNode node) {

    }

    @Override
    public void setAttr(TNode n, Attr attr) {

    }

    @Override
    public TNode setFirstChild(TNode parent, TNode child) {
        return child;
    }

    @Override
    public TNode setRightSibling(TNode l, TNode r) {
        return r;
    }

    @Override
    public void setLeftSibling(TNode l, TNode r) {

    }

    @Override
    public void setChildOfLink(TNode parent, TNode child) {

    }

    @Override
    public void setLink(LinkType relation, TNode node1, TNode node2) {

    }

    @Override
    public TNode getRoot() {
        return null;
    }

    @Override
    public EntityType getType(TNode node) {
        return null;
    }

    @Override
    public Attr getAttr(TNode node) {
        return null;
    }

    @Override
    public TNode getFirstChild(TNode p) {
        return null;
    }

    @Override
    public TNode getLinkedNode(LinkType link, TNode node1) {
        return null;
    }

    @Override
    public Boolean isLinked(LinkType link, TNode node1, TNode node2) {
        return null;
    }

    @Override
    public void setParent(TNode p, TNode c) {

    }

    @Override
    public TNode getParent(TNode c) {
        return null;
    }

    @Override
    public List<TNode> getParentedBy(TNode p) {
        return null;
    }

    @Override
    public TNode getParent$(TNode c) {
        return null;
    }

    @Override
    public List<TNode> getParented$By(TNode p) {
        return null;
    }

    @Override
    public void setFollows(TNode p, TNode c) {

    }

    @Override
    public TNode getFollows(TNode n) {
        return null;
    }

    @Override
    public List<TNode> getFollows$(TNode n) {
        return null;
    }

    @Override
    public TNode getFollowedBy(TNode n) {
        return null;
    }

    @Override
    public List<TNode> getFollowed$By(TNode n) {
        return null;
    }

    @Override
    public Boolean isFollowed(TNode n1, TNode n2) {
        return null;
    }

    @Override
    public Boolean isFollowed$(TNode n1, TNode n2) {
        return null;
    }

    @Override
    public Boolean isParent(TNode p, TNode c) {
        return null;
    }

    @Override
    public Boolean isParent$(TNode p, TNode c) {
        return null;
    }
}
