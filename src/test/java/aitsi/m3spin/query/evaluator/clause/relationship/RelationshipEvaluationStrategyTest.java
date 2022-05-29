package aitsi.m3spin.query.evaluator.clause.relationship;

import aitsi.m3spin.query.evaluator.QueryTestingData;
import aitsi.m3spin.query.evaluator.exception.BadRelationshipArgumentsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipEvaluationStrategyTest extends QueryTestingData {

    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void evaluateForNode_BadArgsForParentRel_BadArgsExceptionThrown() {
        RelationshipEvaluationStrategy parentStrategy = new ParentEvaluationStrategy();

        assertThrows(BadRelationshipArgumentsException.class, () -> parentStrategy.evaluate(procedure, assignment, pkb));
    }

    @Test
    void evaluateForNode_BadArgsForUsesRel_BadArgsExceptionThrown() {
        RelationshipEvaluationStrategy strategy = new UsesEvaluationStrategy();

        assertThrows(BadRelationshipArgumentsException.class, () -> strategy.evaluate(procedure, assignment, pkb));
    }

    @Test
    void evaluateForNode_FollowsRel_EvaluatedTrue() throws BadRelationshipArgumentsException {
        RelationshipEvaluationStrategy strategy = new FollowsEvaluationStrategy();

        assertTrue(strategy.evaluate(whileStmt, assignment, pkb));
    }

    @Test
    void evaluateForNode_FollowsRel_EvaluatedFalse() throws BadRelationshipArgumentsException {
        RelationshipEvaluationStrategy strategy = new FollowsEvaluationStrategy();

        assertFalse(strategy.evaluate(assignment, whileStmt, pkb));
    }
}
