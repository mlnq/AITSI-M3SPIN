package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

public abstract class RelationshipEvaluationStrategy {
    protected final RelationshipEvaluatorEnum relationship;

    protected RelationshipEvaluationStrategy(RelationshipEvaluatorEnum relationship) {
        this.relationship = relationship;
    }

    boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) {
        return areNodeTypesValid(firstNode, secondNode);
    }

    abstract boolean areNodeTypesValid(TNode firstNode, TNode secondNode);
}
