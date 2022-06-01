package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Statement;
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
        List<Statement> childList = new ArrayList<>();

        while (currentStmt.getRightSibling() != null) {
            childList.add(currentStmt);
            currentStmt = (Statement) currentStmt.getRightSibling();
        }
        return childList;
    }

    @Override
    public Statement getParent(Statement child) {
        while (child.getParent() == null) {
            child = (Statement) child.getLeftSibling();
        }
        return (Statement) child.getParent();
    }

    @Override
    public List<Statement> getParentT(Statement child) {
        return null;//todo po 1 iteracji
    }

    @Override
    public List<Statement> getParentedByT(Statement parent) {
        return null;//todo po 1 iteracji
    }

    @Override
    public boolean isParent(Statement parent, Statement c) {
        return c.getParent().equals(parent);
    }

    @Override
    public boolean isParentT(Statement parent, Statement c) {
        return false;
    }
}
