package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.query.evaluator.QueryTestingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RelationshipEvaluationStrategyTest extends QueryTestingData {

    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();
    }

    @Test
    void evaluateForNode_BadArgsForParentRel_ReturnedFalse() {
        RelationshipEvaluationStrategy parentStrategy = new ParentEvaluationStrategy();

        assertFalse(parentStrategy.evaluate(procedure, assignment, pkb));
    }

    @Test
    void evaluateForNode_BadArgsForUsesRel_ReturnedFalse() {
        RelationshipEvaluationStrategy strategy = new UsesEvaluationStrategy();

        assertFalse(strategy.evaluate(procedure, assignment, pkb));
    }

    @Test
    void evaluateForNode_FollowsRel_EvaluatedTrue() {
        RelationshipEvaluationStrategy strategy = new FollowsEvaluationStrategy();

        assertTrue(strategy.evaluate(whileStmt, assignment, pkb));
    }

    @Test
    void evaluateForNode_FollowsRel_EvaluatedFalse() {
        RelationshipEvaluationStrategy strategy = new FollowsEvaluationStrategy();

        assertFalse(strategy.evaluate(assignment, whileStmt, pkb));
    }
}
