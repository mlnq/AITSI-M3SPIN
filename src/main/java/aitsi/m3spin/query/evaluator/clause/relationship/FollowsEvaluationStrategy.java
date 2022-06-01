package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.exception.BadRelationshipArgumentsException;
import aitsi.m3spin.query.model.enums.RelationshipEnum;

public class FollowsEvaluationStrategy extends TwoStmtsAsArgsEvaluationStrategy {
    protected FollowsEvaluationStrategy() {
        super(RelationshipEnum.FOLLOWS);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) throws BadRelationshipArgumentsException {
        if (!super.evaluate(firstNode, secondNode, pkb)) return false;
        Statement actualStmtWhichFollowsFirst = pkb.getFollowsInterface().getFollows((Statement) firstNode);
        return secondNode.equals(actualStmtWhichFollowsFirst);
    }
}
