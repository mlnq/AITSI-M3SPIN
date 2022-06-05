package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.query.QueryProcessorException;
import aitsi.m3spin.query.evaluator.QueryTestingData;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuchThatEvaluatorTest extends QueryTestingData {
    private SuchThatEvaluator suchThatEvaluator;
    private TNodeDao tNodeDao;

    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();
        tNodeDao = new TNodeDao(pkb);
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void testForClause() {
//    }
//
//    @Test
//    void testEvaluateBooleanClause() {
//    }
//
//    @Test
//    void testEvaluateBooleanClause1() {
//    }

    @Test
    void EvaluateClause_WithPreviousResults_ReturnedAllProcedures() throws QueryProcessorException {//todo powinno zwracać wszystkie procedury
        suchThatEvaluator = new SuchThatEvaluator(pkb, tNodeDao, suchThat);

        assertEquals(procedureResult, suchThatEvaluator.evaluateClause(new TNodeSetResult(Collections.emptySet()), procSynonym));//todo wywala się
    }

    @Test
    void EvaluateClause_WithPreviousResults_Evaluated() throws QueryProcessorException {
        suchThatEvaluator = new SuchThatEvaluator(pkb, tNodeDao, suchThat);

        assertEquals(assignResult, suchThatEvaluator.evaluateClause(new TNodeSetResult(Collections.emptySet()), assignSynonym));//todo
    }
}
