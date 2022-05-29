package aitsi.m3spin.query.evaluator.dao;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractNodeFinder {

    protected Set<TNode> findAllBy(TNode startingNode, Pkb pkb) {
        Set<TNode> nodeSet = new HashSet<>();
        if (checkSearchCriteria(startingNode)) {
            nodeSet.add(startingNode);
        }
        if (pkb.getAst().hasChild(startingNode))
            nodeSet.addAll(findAllBy(startingNode.getChild(), pkb));
        if (pkb.getAst().hasRightSibling(startingNode))
            nodeSet.addAll(findAllBy(startingNode.getRightSibling(), pkb));

        return nodeSet;
    }

    protected abstract boolean checkSearchCriteria(final TNode startingNode);
}
