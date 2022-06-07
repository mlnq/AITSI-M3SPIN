package aitsi.m3spin.query.evaluator.dao;

import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.AttributableNode;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NodeFinderByAttribute extends AbstractNodeFinder {
    private final NodeAttribute attribute;


    public static Set<AttributableNode> find(TNode startingNode, Pkb pkb, NodeAttribute attribute) {
        return new NodeFinderByAttribute(attribute).findAllBy(startingNode, pkb).stream()
                .map(AttributableNode.class::cast)
                .collect(Collectors.toSet());
    }

    @Override
    protected boolean checkSearchCriteria(final TNode startingNode) {
        return startingNode.hasAttribute() && ((AttributableNode) startingNode).getAttribute().equals(attribute);
    }
}
