package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Statement;

import java.util.Set;

public interface Follows {
    void setFollows(Statement p, Statement c);

    Statement getFollows(Statement statement);

    Set<Statement> getFollowsT(Statement statement);

    Statement getFollowedBy(Statement statement);

    Set<Statement> getFollowedByT(Statement statement);

    boolean isFollowed(Statement statement1, Statement statement2);

    boolean isFollowedT(Statement statement1, Statement statement2);
}
