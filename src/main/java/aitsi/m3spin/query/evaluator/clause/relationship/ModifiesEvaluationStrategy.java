package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;

import java.util.Set;

public class ModifiesEvaluationStrategy extends VarAsSecondArgEvaluationStrategy {
    protected ModifiesEvaluationStrategy() {
        super(RelationshipEvaluatorEnum.MODIFIES);
    }

    @Override
    public boolean evaluate(TNode firstNode, TNode secondNode, Pkb pkb) {
        if (!super.evaluate(firstNode, secondNode, pkb)) return false;
        if (EntityType.PROCEDURE.equals(firstNode.getType())) {
            Set<Variable> varsModifiedByProc = pkb.getModifiesInterface().getModified((Procedure) firstNode);
            return varsModifiedByProc.contains(secondNode);
        } else {
            Set<Variable> varsUsedByStmt = pkb.getUsesInterface().getVarsUsedByStmt((Statement) firstNode);
            return varsUsedByStmt.contains(secondNode);
        }
    }
}
