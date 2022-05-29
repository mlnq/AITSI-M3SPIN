package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.exception.BadRelationshipArgumentsException;
import aitsi.m3spin.query.model.enums.RelationshipEnum;

import java.util.List;

public class ParentEvaluationStrategy extends TwoStmtsAsArgsEvaluationStrategy {
    protected ParentEvaluationStrategy() {
        super(RelationshipEnum.PARENT);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) throws BadRelationshipArgumentsException {
        areNodeTypesValid(firstNode, secondNode);
        List<Statement> stmtsParentedByFirst = pkb.getParentInterface().getParentedBy((Statement) firstNode);
        return stmtsParentedByFirst.contains(secondNode);
    }
}
