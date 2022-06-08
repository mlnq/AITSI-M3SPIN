package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

public abstract class TwoProcsAsArgsEvaluationStrategy extends RelationshipEvaluationStrategy {
    protected TwoProcsAsArgsEvaluationStrategy(RelationshipEvaluatorEnum relationship) {
        super(relationship);
    }

    @Override
    public boolean areNodeTypesValid(TNode firstNode, TNode secondNode) {
        return firstNode instanceof Procedure && secondNode instanceof Procedure;
    }
}
