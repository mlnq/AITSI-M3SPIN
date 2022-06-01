package aitsi.m3spin.query.evaluator.dao;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.query.model.references.IntegerReference;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class TNodeDao {
    private final Pkb pkb;

    public Set<TNode> findAllConstants(int constValue, TNode startingNode) {
        return findAllByTypeAndAttr(startingNode, EntityType.CONSTANT, new IntegerReference(constValue));
    }

    private Set<TNode> findAllByType(TNode startingNode, EntityType type) {
        return NodeFinderByType.find(startingNode, pkb, type);
    }

    public Set<TNode> findAllByType(EntityType type) {
        return findAllByType(pkb.getAst().getRoot(), type);
    }

    private Set<TNode> findAllByTypeAndAttr(TNode startingNode, EntityType type, NodeAttribute attribute) {
        return NodeFinderByTypeAndAttribute.find(startingNode, pkb, type, attribute);
    }

    Set<TNode> findAllByTypeAndAttr(EntityType type, NodeAttribute attribute) {
        return findAllByTypeAndAttr(pkb.getAst().getRoot(), type, attribute);
    }

    private Set<AttributableNode> findAllByAttribute(TNode startingNode, NodeAttribute attribute) {
        return NodeFinderByAttribute.find(startingNode, pkb, attribute);
    }

    public Set<AttributableNode> findAllByAttribute(NodeAttribute attribute) {
        return findAllByAttribute(pkb.getAst().getRoot(), attribute);
    }

    public Set<TNode> findAll() {
        return NodeFinderAll.find(pkb.getAst().getRoot(), pkb);
    }
}
