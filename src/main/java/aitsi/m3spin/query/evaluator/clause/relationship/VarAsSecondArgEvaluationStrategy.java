package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.query.evaluator.exception.BadRelationshipArgumentsException;
import aitsi.m3spin.query.model.enums.RelationshipEnum;

public abstract class VarAsSecondArgEvaluationStrategy extends RelationshipEvaluationStrategy {
    protected VarAsSecondArgEvaluationStrategy(RelationshipEnum relationship) {
        super(relationship);
    }

    @Override
    public void areNodeTypesValid(TNode firstNode, TNode secondNode) throws BadRelationshipArgumentsException {
        if (!((firstNode instanceof Procedure || firstNode instanceof Statement) && secondNode instanceof Variable))
            throw new BadRelationshipArgumentsException(
                    this.relationship, firstNode.getType(), secondNode.getType());
    }
}
