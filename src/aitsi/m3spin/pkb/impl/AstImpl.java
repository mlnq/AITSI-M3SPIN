package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.interfaces.Ast;
import aitsi.m3spin.pkb.exception.IllegalLinkTypeException;
import aitsi.m3spin.pkb.exception.IllegalNodeTypeException;
import aitsi.m3spin.pkb.interfaces.Follows;
import aitsi.m3spin.pkb.interfaces.Parent;

import java.util.List;

public class AstImpl implements Ast, Follows, Parent {
    private int procId = 0;
    private int varId = 0;
    private TNode root;

    @Override
    public TNode createTNode(EntityType et) throws IllegalNodeTypeException {
        switch (et){
            case ASSIGNMENT:
                return new AssignmentImpl();
            case CONSTANT:
                return new ConstantImpl();
            case IF:
                return new IfImpl();
            case MINUS:
                return new MinusImpl();
            case PLUS:
                return new PlusImpl();
            case PROCEDURE:
                return new ProcedureImpl(procId++);
            case VARIABLE:
                return new VariableImpl(varId++);
            case WHILE:
                return new WhileImpl();
            default:
                throw new IllegalNodeTypeException(et);
        }
    }

    @Override
    public TNode setRoot(TNode node) {
        this.root = node;
        return node;
    }

    @Override
    public void setAttr(TNode n, String attr) {
        n.setAttribute(attr);
    }

    @Override
    public TNode setFirstChild(TNode parent, TNode child) {
        parent.setFirstChild(child);
        child.setParent(parent);
        return child;
    }

    @Override
    public TNode setSibling(TNode left, TNode right) {
        left.setRightSibling(right);
        right.setLeftSibling(left);
        return right;
    }

    @Override
    public TNode setLink(LinkType relation, TNode node1, TNode node2) throws IllegalLinkTypeException {
        switch (relation)
        {
            case CHILD:
                node2.setChild(node1);
                node1.setParent(node2);
                return node2;
            case PARENT:
                node1.setChild(node2);
                node2.setParent(node1);
                return node1;
            case SIBLING:
                node1.setRightSibling(node2);
                node2.setLeftSibling(node1);
                return node2;
            default:
                throw new IllegalLinkTypeException(relation, node1, node2);
        }
    }

    @Override
    public TNode getRoot() {
        return root;
    }

    @Override
    public EntityType getType(TNode node) {
        return node.getType();
    }

    @Override
    public String getAttr(TNode node) {
        return node.getAttribute();
    }

    @Override
    public TNode getChild(TNode p) {
        return p.getChild();
    }

    @Override
    public TNode getLinkedNode(LinkType link, TNode node) throws IllegalLinkTypeException {
        switch(link) {
            case CHILD:
                return node.getChild();
            case PARENT:
                return node.getParent();
            case SIBLING:
                return node.getRightSibling();
            default:
                throw new IllegalLinkTypeException(link);
        }
    }

    @Override
    public Boolean isLinked(LinkType link, TNode node1, TNode node2) throws IllegalLinkTypeException {
        TNode linkedNode;
        switch (link) {
            case CHILD:
                linkedNode = node1.getChild();
                break;
            case PARENT:
                linkedNode = node1.getParent();
                break;
            case SIBLING:
                linkedNode = node1.getRightSibling();
                break;
            default:
                throw new IllegalLinkTypeException(link);
        }
        if (linkedNode == null) {
            return false;
        }
        return linkedNode.equals(node2);
    }

    @Override
    public TNode setParent(TNode parent, TNode child) {
        child.setParent(parent);
        parent.setChild(child);
        return parent;
    }

    @Override
    public TNode getParent(TNode c) {
        return c.getParent();
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
        return c.getParent() == p;
    }

    @Override
    public Boolean isParent$(TNode p, TNode c) {
        return null;
    }
}