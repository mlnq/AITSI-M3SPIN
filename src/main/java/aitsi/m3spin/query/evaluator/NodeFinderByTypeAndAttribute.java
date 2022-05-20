package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class NodeFinderByTypeAndAttribute extends AbstractNodeFinder {
    private final EntityType type;
    private final String attrValue;


    public static Set<TNode> find(TNode startingNode, Pkb pkb, EntityType type, String attrValue){
        return new NodeFinderByTypeAndAttribute(type, attrValue).findAllBy(startingNode, pkb);
    }

    @Override
    protected boolean checkSearchCriteria(final TNode startingNode) {
        return startingNode.getType().equals(type) && startingNode.getAttribute().equals(attrValue);
    }
}
