package aitsi.m3spin.query.evaluator.dao;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.pkb.model.IntegerAttribute;
import aitsi.m3spin.query.model.references.IntegerReference;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

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
        if(type == EntityType.STATEMENT){
            return findAllStmts();
        }else{
            return findAllByType(pkb.getAst().getRoot(), type);
        }
    }

    public Set<TNode> findAllByTypeAndAttr(TNode startingNode, EntityType type, NodeAttribute attribute) {
        return NodeFinderByTypeAndAttribute.find(startingNode, pkb, type, attribute);
    }

    public Set<TNode> findAllByTypeAndAttr(EntityType type, NodeAttribute attribute) {
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

    public Set<TNode> findAllStmts() {
        Set<TNode> allStmts = findAllByType(EntityType.ASSIGNMENT);
        allStmts.addAll(findAllByType(EntityType.WHILE));
        allStmts.addAll(findAllByType(EntityType.IF));
        allStmts.addAll(findAllByType(EntityType.CALL));
        return allStmts;
    }

    public Set<TNode> findAllByProgLine(IntegerAttribute progLine) {
        Set<TNode> allStmts = findAllStmts();
        return allStmts.stream()
                .map(AttributableNode.class::cast)
                .filter(node -> node.getAttribute().equals(progLine))
                .collect(Collectors.toSet());
    }
}
