package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.query.QueryProcessorException;
import aitsi.m3spin.query.evaluator.QueryTestingData;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SuchThatEvaluatorTest extends QueryTestingData {
    private SuchThatEvaluator suchThatEvaluator;
    private TNodeDao tNodeDao;

    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();
        tNodeDao = new TNodeDao(pkb);
    }

    @Test
    void EvaluateClause_WithPreviousResults_ReturnedBothResults() throws QueryProcessorException {
        suchThatEvaluator = new SuchThatEvaluator(pkb, tNodeDao, followsSuchThat);

        TNodeSetResult[] bothResults = new TNodeSetResult[]{whileResult, assignResult};
        assertArrayEquals(bothResults, suchThatEvaluator.evaluateClause());
    }

    @Test
    void EvaluateClause_Follows_ReturnedBothResults() throws QueryProcessorException {
        suchThatEvaluator = new SuchThatEvaluator(pkb, tNodeDao, followsSuchThat);

        TNodeSetResult[] bothResults = new TNodeSetResult[]{whileResult, assignResult};
        assertArrayEquals(bothResults, suchThatEvaluator.evaluateClause());
    }
}
