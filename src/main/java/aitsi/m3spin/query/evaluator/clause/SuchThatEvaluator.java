package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.RelationEnum;
import aitsi.m3spin.query.model.Synonym;

import java.util.HashSet;
import java.util.Set;

public class SuchThatEvaluator extends ClauseEvaluator{
    public SuchThatEvaluator(Pkb pkb, TNodeDao tNodeDao) {
        super(pkb, tNodeDao);
    }

    @Override
    public Set<TNode> evaluateClause(Set<TNode> previousResult) {
        Set<TNode> result = new HashSet<>();
        Set<TNode> firstArgumentNodes = getNodesFor(suchThat.getFirstArgument());
        Set<TNode> secondArgumentNodes = getNodesFor(suchThat.getSecondArgument());

        RelationEnum relation = suchThat.getRelation();
        firstArgumentNodes.forEach(node -> {
            if (suchThat.getFirstArgument() instanceof Synonym)
                findNodesInRelation(node, relation);
        });
        return result; //todo
    }
}
