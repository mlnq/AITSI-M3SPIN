package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.interfaces.Follows;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FollowsImpl implements Follows {
    HashMap<Statement, Statement> follows = new HashMap<>();

    @Override
    public void setFollows(Statement previous, Statement next) {
        follows.put(previous, next);
    }

    @Override
    public Statement getFollows(Statement previous) {
        return follows.get(previous);
    }

    @Override
    public Set<Statement> getFollowsT(Statement previous) {
        HashSet<Statement> followsTResult = new HashSet<>();
        Statement current = previous;
        while (follows.containsKey(current)) {
            current = getFollows(current);
            followsTResult.add(current);
        }
        return new HashSet<>(followsTResult);
    }

    @Override
    public Statement getFollowedBy(Statement next) {
        for (Map.Entry<Statement, Statement> s: follows.entrySet()) {
            if (s.getValue().equals(next)) {
                return s.getValue();
            }
        }
        return null;
    }

    @Override
    public HashSet<Statement> getFollowedByT(Statement next) {
        HashSet<Statement> followedByTResult = new HashSet<>();
        Statement current = next;
        while (follows.containsValue(current)) {
            current = getFollowedBy(current);
            followedByTResult.add(current);
        }
        return new HashSet<>(followedByTResult);
    }

    @Override
    public boolean isFollowed(Statement previous, Statement next) {
        return follows.get(previous).equals(next);
    }

    @Override
    public boolean isFollowedT(Statement previous, Statement next) {
        Statement current =  previous;
        while (follows.containsKey(current)) {
            current = getFollows(current);
            if (current.equals(next))
                return true;
        }
        return false;
    }
}
