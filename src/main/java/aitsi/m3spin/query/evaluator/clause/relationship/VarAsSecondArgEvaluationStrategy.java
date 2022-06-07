package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

public abstract class VarAsSecondArgEvaluationStrategy extends RelationshipEvaluationStrategy {
    protected VarAsSecondArgEvaluationStrategy(RelationshipEvaluatorEnum relationship) {
        super(relationship);
    }

    @Override
    public boolean areNodeTypesValid(TNode firstNode, TNode secondNode) {
        return (firstNode instanceof Procedure || firstNode instanceof Statement) && secondNode instanceof Variable;
    }
}
