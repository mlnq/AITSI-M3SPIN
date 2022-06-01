package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.exception.BadRelationshipArgumentsException;
import aitsi.m3spin.query.model.enums.RelationshipEnum;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class RelationEvaluator {
    private final Pkb pkb;
    private RelationshipEvaluationStrategy strategy;

    public static RelationshipEvaluationStrategy chooseEvaluationStrategy(RelationshipEnum relationship) {
        switch (relationship) {
            case FOLLOWS_T:
                return new FollowsTEvaluationStrategy();
            case FOLLOWS:
                return new FollowsEvaluationStrategy();
            case PARENT_T:
                return new ParentTEvaluationStrategy();
            case PARENT:
                return new ParentEvaluationStrategy();
            case USES:
                return new UsesEvaluationStrategy();
            case MODIFIES:
                return new ModifiesEvaluationStrategy();
            default:
                return null;
        }
    }

    public boolean evaluateRelationshipForNode(TNode firstNode, TNode secondNode) throws BadRelationshipArgumentsException {
        return strategy.evaluate(firstNode, secondNode, pkb);
    }
}
