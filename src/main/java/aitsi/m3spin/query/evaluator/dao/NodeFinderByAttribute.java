package aitsi.m3spin.query.evaluator.dao;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class NodeFinderByAttribute extends AbstractNodeFinder {
    private final String attrValue;


    public static Set<TNode> find(TNode startingNode, Pkb pkb, String attrValue) {
        return new NodeFinderByAttribute(attrValue).findAllBy(startingNode, pkb);
    }

    @Override
    protected boolean checkSearchCriteria(final TNode startingNode) {
        return startingNode.getAttribute().equals(attrValue);
    }
}
