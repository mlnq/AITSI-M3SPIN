package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

import java.util.Set;
import java.util.stream.Collectors;

public class ModifiesEvaluationStrategy extends VarAsSecondArgEvaluationStrategy {
    protected ModifiesEvaluationStrategy() {
        super(RelationshipEvaluatorEnum.MODIFIES);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) {
        if (!super.evaluate(firstNode, secondNode, pkb)) return false;
        if (EntityType.PROCEDURE.equals(firstNode.getType())) {
            Set<TNode> varsModifiedByProc = pkb.getModifiesInterface().getModified((Procedure) firstNode).stream()
                    .map(TNode.class::cast)
                    .collect(Collectors.toSet());
            return varsModifiedByProc.contains(secondNode);
        } else {
            Set<TNode> varsUsedByStmt = pkb.getModifiesInterface().getModified((Statement) firstNode).stream()
                    .map(TNode.class::cast)
                    .collect(Collectors.toSet());
            return varsUsedByStmt.contains(secondNode);
        }
    }
}
