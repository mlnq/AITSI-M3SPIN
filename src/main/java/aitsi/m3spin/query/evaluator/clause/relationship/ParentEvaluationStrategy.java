package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

import java.util.Set;

public class ParentEvaluationStrategy extends TwoStmtsAsArgsEvaluationStrategy {
    protected ParentEvaluationStrategy() {
        super(RelationshipEvaluatorEnum.PARENT);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) {
        if (!super.evaluate(firstNode, secondNode, pkb)) return false;
        Set<Statement> stmtsParentedByFirst = pkb.getParentInterface().getParentedBy((Statement) firstNode);
        return stmtsParentedByFirst.contains(secondNode);
    }
}
