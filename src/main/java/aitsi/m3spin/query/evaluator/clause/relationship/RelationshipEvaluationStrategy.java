package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.exception.BadRelationshipArgumentsException;
import aitsi.m3spin.query.model.enums.RelationshipEnum;

public abstract class RelationshipEvaluationStrategy {
    protected final RelationshipEnum relationship;

    protected RelationshipEvaluationStrategy(RelationshipEnum relationship) {
        this.relationship = relationship;
    }

    boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) throws BadRelationshipArgumentsException {
        return areNodeTypesValid(firstNode, secondNode);
    }

    abstract boolean areNodeTypesValid(TNode firstNode, TNode secondNode);
}
