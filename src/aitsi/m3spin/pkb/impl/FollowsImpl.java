package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.interfaces.Follows;

import java.util.ArrayList;
import java.util.List;

public class FollowsImpl implements Follows {

    @Override
    public void setFollows(Statement l, Statement r) {
        l.setRightSibling(r);
        r.setLeftSibling(l);
    }

    @Override
    public Statement getFollows(Statement statement) {
        return (Statement) statement.getRightSibling();
    }

    @Override
    public List<Statement> getFollowsT(Statement statement) {
        return null;
    }

    @Override
    public Statement getFollowedBy(Statement statement) {
        return (Statement) statement.getLeftSibling();
    }

    @Override
    public List<Statement> getFollowed$By(Statement statement) {
        return null;
    }

    @Override
    public Boolean isFollowed(Statement statement1, Statement statement2) {
        return statement1.getRightSibling() == statement2;
    }

    @Override
    public Boolean isFollowedT(Statement statement1, Statement statement2) {
        return null;
    }
}
