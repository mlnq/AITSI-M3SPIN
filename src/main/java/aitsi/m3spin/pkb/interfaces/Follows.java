package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Statement;

import java.util.List;

public interface Follows {
    void setFollows(Statement p, Statement c);

    Statement getFollows(Statement statement);

    List<Statement> getFollowsT(Statement statement);

    Statement getFollowedBy(Statement statement);

    List<Statement> getFollowedByT(Statement statement);

    boolean isFollowed(Statement statement1, Statement statement2);

    boolean isFollowedT(Statement statement1, Statement statement2);
}
