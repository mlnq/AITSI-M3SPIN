package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.interfaces.Follows;

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
        return null; //todo po pierwszej iteracji
    }

    @Override
    public Statement getFollowedBy(Statement statement) {
        return (Statement) statement.getLeftSibling();
    }

    @Override
    public List<Statement> getFollowedByT(Statement statement) {
        return null;//todo po 1 iteracji
    }

    @Override
    public boolean isFollowed(Statement statement1, Statement statement2) {
        return statement1.getRightSibling().equals(statement2);
    }

    @Override
    public boolean isFollowedT(Statement statement1, Statement statement2) {
        return false;
    }
}
