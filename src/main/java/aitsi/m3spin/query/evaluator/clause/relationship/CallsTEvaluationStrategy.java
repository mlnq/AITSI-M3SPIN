package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

import java.util.List;

public class CallsTEvaluationStrategy extends TwoProcsAsArgsEvaluationStrategy {
    protected CallsTEvaluationStrategy() {
        super(RelationshipEvaluatorEnum.CALLS_T);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) {
        if (!super.evaluate(firstNode, secondNode, pkb)) return false;
        List<Procedure> proceduresCalledBy = pkb.getCallsInterface().getCalledByT((Procedure) firstNode);
        return proceduresCalledBy.contains(secondNode);
    }
}
