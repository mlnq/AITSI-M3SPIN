package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

public class FollowsEvaluationStrategy extends TwoStmtsAsArgsEvaluationStrategy {
    protected FollowsEvaluationStrategy() {
        super(RelationshipEvaluatorEnum.FOLLOWS);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) {
        if (!super.evaluate(firstNode, secondNode, pkb)) return false;
        Statement actualStmtWhichFollowsFirst = pkb.getFollowsInterface().getFollows((Statement) firstNode);
        return secondNode.equals(actualStmtWhichFollowsFirst);
    }
}
