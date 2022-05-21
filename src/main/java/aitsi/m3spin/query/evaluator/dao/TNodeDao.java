package aitsi.m3spin.query.evaluator.dao;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class TNodeDao {
    private final Pkb pkb;

    public Set<TNode> findAllConstants(int constValue, TNode startingNode) {
        return findAllByTypeAndAttr(startingNode, EntityType.CONSTANT, String.valueOf(constValue));
    }

    public Set<TNode> findAllByType(TNode startingNode, EntityType type) {
        return NodeFinderByType.find(startingNode, pkb, type);
    }

    Set<TNode> findAllByTypeAndAttr(TNode startingNode, EntityType type, String attribute) {
        return NodeFinderByTypeAndAttribute.find(startingNode, pkb, type, attribute);
    }

    public Set<TNode> findAllByAttribute(TNode startingNode, String attribute) {
        return NodeFinderByAttribute.find(startingNode, pkb, attribute);
    }


}
