package aitsi.m3spin.query.evaluator.dao;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;

import java.util.HashSet;
import java.util.Set;

public class NodeFinderAll extends AbstractNodeFinder {
    public static Set<TNode> find(TNode startingNode, Pkb pkb) {
        return new HashSet<>(new NodeFinderAll().findAllBy(startingNode, pkb));
    }

    @Override
    protected boolean checkSearchCriteria(TNode startingNode) {
        return true;
    }
}
