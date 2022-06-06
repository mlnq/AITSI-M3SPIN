package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

import java.util.List;
import java.util.stream.Collectors;

public class FollowsTEvaluationStrategy extends TwoStmtsAsArgsEvaluationStrategy {
    protected FollowsTEvaluationStrategy() {
        super(RelationshipEvaluatorEnum.FOLLOWS_T);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) {
        if (!super.evaluate(firstNode, secondNode, pkb)) return false;
        List<TNode> actualStmtsWhichFollowFirst = pkb.getFollowsInterface().getFollowsT((Statement) firstNode)
                .stream().map(TNode.class::cast).collect(Collectors.toList());
        return actualStmtsWhichFollowFirst.contains(secondNode);
    }
}
