package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

import java.util.Set;

public class CallsEvaluationStrategy extends TwoProcsAsArgsEvaluationStrategy {
    protected CallsEvaluationStrategy() {
        super(RelationshipEvaluatorEnum.CALLS);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) {
        if (!super.evaluate(firstNode, secondNode, pkb)) return false;
        Set<Procedure> proceduresCalledBy = pkb.getCallsInterface().getCalledBy((Procedure) firstNode);
        return proceduresCalledBy.contains((secondNode));
    }
}
