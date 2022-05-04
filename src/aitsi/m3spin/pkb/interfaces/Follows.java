package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface Follows {
    void setFollows(Statement p, Statement c);

    Statement getFollows(Statement statement);

    List<Statement> getFollowsT(Statement statement);

    Statement getFollowedBy(Statement statement);

    List<Statement> getFollowedByT(Statement statement);

    Boolean isFollowed(Statement statement1, Statement statement2);

    Boolean isFollowedT(Statement statement1, Statement statement2);
}
