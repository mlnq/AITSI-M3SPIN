package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.query.evaluator.exception.BadRelationshipArgumentsException;
import aitsi.m3spin.query.model.enums.RelationshipEnum;

public abstract class TwoStmtsAsArgsEvaluationStrategy extends RelationshipEvaluationStrategy {
    protected TwoStmtsAsArgsEvaluationStrategy(RelationshipEnum relationship) {
        super(relationship);
    }

    @Override
    public boolean areNodeTypesValid(TNode firstNode, TNode secondNode) throws BadRelationshipArgumentsException {
        if (!(firstNode instanceof Statement && secondNode instanceof Statement))
            throw new BadRelationshipArgumentsException(
                    this.relationship, firstNode.getType(), secondNode.getType());
    }
}
