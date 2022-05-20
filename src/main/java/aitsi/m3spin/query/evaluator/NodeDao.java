package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class NodeDao {
    private final Pkb pkb;

    Set<TNode> findAllConstants(int constValue, TNode startingNode) {
        return findAllByTypeAndAttr(startingNode, EntityType.CONSTANT, String.valueOf(constValue));
    }

    Set<TNode> findAllByType(TNode startingNode, EntityType type) {
        return NodeFinderByType.find(startingNode, pkb, type);
    }

    Set<TNode> findAllByTypeAndAttr(TNode startingNode, EntityType type, String attribute) {
        return NodeFinderByTypeAndAttribute.find(startingNode, pkb, type, attribute);
    }

    Set<TNode> findAllByAttribute(TNode startingNode, String attribute) {
        return NodeFinderByAttribute.find(startingNode, pkb, attribute);
    }


}
