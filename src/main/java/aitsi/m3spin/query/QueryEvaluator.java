package aitsi.m3spin.query;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.*;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class QueryEvaluator {
    private Pkb pkb;

    public Set<TNode> evaluateQuery(Query query) {

        Set<TNode> foundNodes = evaluateWithList(query.getWithList());
        Declaration select = query.getSelect();
        if (foundNodes.isEmpty()) {
            foundNodes = findAllNodesByTypeFor(select.getType(), pkb.getAst().getRoot());
        }
        Set<TNode> suchThatResult = evaluateSuchThatList(query.getSuchThatList(),foundNodes, select);

        return result;
    }

    private Set<TNode> evaluateWithList(List<WithClause> withList) {
        Set<TNode> result = new HashSet<>();
        withList.forEach(withClause -> {
            result.addAll(evaluateWithClause(withClause));
        });
        return result;
    }

    private Set<TNode> evaluateWithClause(WithClause withClause) {
        withClause.getSynonym().getType().equals(withClause.getAttribute().g)
        Set<TNode> nodes = findAllNodesByTypeFor(withClause.getSynonym().getType(), pkb.getAst().getRoot());
        nodes.stream()
                .filter(node -> node.getAttribute())
        return nodes;
    }

    private Set<TNode> findAllNodesByTypeFor(EntityType type, TNode node) {
        Set<TNode> nodeSet = new HashSet<>();
        if (node.getType().equals(type)) nodeSet.add(node);
        if (pkb.getAst().hasChild(node))
            nodeSet.addAll(findAllNodesByTypeFor(type, pkb.getAst().getChild(node)));
        if (pkb.getAst().hasRightSibling(node))
            nodeSet.addAll(findAllNodesByTypeFor(type, pkb.getAst().getRightSibling(node)));
        return nodeSet;
    }

    private Set<TNode> evaluateSuchThatList(List<SuchThat> suchThatList, Set<TNode> foundNodes, Declaration searched) {
        suchThatList.forEach(suchThat -> {
            RelationArgument firstArgument = suchThat.getFirstArgument();
            RelationArgument secondArgument = suchThat.getSecondArgument();
            boolean isFirstSearched = false;
            if (firstArgument instanceof Declaration) {
                Declaration argument1 = (Declaration) firstArgument;
                if (argument1.equals(searched)) {
                    isFirstSearched = true;
                }
            } else if (firstArgument instanceof Constant) {
                Constant argument1 = (Constant) firstArgument;
            } else {
                SimpleEntityName argument1 = (SimpleEntityName) firstArgument;
            }
            RelationEnum relation = suchThat.getRelation();

            switch (relation) {
                case FOLLOWS_T:
                case FOLLOWS:
                    pkb.getFollowsInterface();
                    break;
                case PARENT_T:
                case PARENT:
                    pkb.getParentInterface();
                    break;
                case USES:
                    pkb.getUsesInterface();
                    break;
                case MODIFIES:
                    pkb.getModifiesInterface();
                    break;
            }
        });
        return null;
    }
}
