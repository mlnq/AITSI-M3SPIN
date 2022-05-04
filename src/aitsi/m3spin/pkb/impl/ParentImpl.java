package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.interfaces.Parent;

import java.util.ArrayList;
import java.util.List;

public class ParentImpl implements Parent {

    @Override
    public Statement setParent(Statement parent, Statement child) {
        child.setParent(parent);
        parent.setChild(child);
        return parent;
    }

    @Override
    public List<Statement> getParentedBy(Statement parent) {

        Statement currentStmt = (Statement) parent.getChild();
        List<Statement> childList = new ArrayList<Statement>();

        while(currentStmt.getRightSibling() != null)
        {
            childList.add(currentStmt);
            currentStmt = (Statement) currentStmt.getRightSibling();
        }
        return childList;
    }

    @Override
    public Statement getParent(Statement child) {
        while(child.getParent() == null){
            child = (Statement) child.getLeftSibling();
        }
        return (Statement) child.getParent();
    }

    @Override
    public List<Statement> getParentT(Statement child) {
        return null;
    }

    @Override
    public List<Statement> getParentedByT(Statement parent) {
        return null;
    }

    @Override
    public Boolean isParent(Statement parent, Statement c) {
        return c.getParent().equals(parent);
    }

    @Override
    public Boolean isParentT(Statement parent, Statement c) {
        return null;
    }
}
