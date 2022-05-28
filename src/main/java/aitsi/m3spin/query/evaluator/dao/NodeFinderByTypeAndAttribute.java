package aitsi.m3spin.query.evaluator.dao;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.AttributableNode;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class NodeFinderByTypeAndAttribute extends AbstractNodeFinder {
    private final EntityType type;
    private final NodeAttribute attrValue;


    public static Set<TNode> find(TNode startingNode, Pkb pkb, EntityType type, NodeAttribute attrValue) {
        return new NodeFinderByTypeAndAttribute(type, attrValue).findAllBy(startingNode, pkb);
    }

    @Override
    protected boolean checkSearchCriteria(final TNode startingNode) {
        return startingNode.getType().equals(type) &&
                ((AttributableNode) startingNode).getAttribute().getValue().equals(attrValue);
    }
}
