package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.query.evaluator.exception.QueryEvaluatorException;
import aitsi.m3spin.query.model.Query;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.enums.AttributeEnum;
import aitsi.m3spin.query.model.references.AttributeReference;
import aitsi.m3spin.query.model.references.StringReference;
import aitsi.m3spin.query.model.references.WithArgumentRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryEvaluatorTest extends QueryTestingData {
    private QueryEvaluator queryEvaluator;
    private Query singleSuchThatQuery;
    private Query singleWithQuery;


    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();

        queryEvaluator = new QueryEvaluator(pkb);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void evaluateQueries() {
    }

    @Test
    void evaluateQuery_SingleWithClause_Evaluated() throws QueryEvaluatorException {
        WithArgumentRef leftRef = new AttributeReference(procSynonym, AttributeEnum.PROC_NAME);
        WithArgumentRef rightRef = new StringReference(PROCEDURE_NAME);
        WithClause withClause = new WithClause(leftRef, rightRef);

        singleWithQuery = new Query(
                selectedResult,
                Collections.emptyList(),
                Collections.singletonList(withClause),
                Collections.emptyList()
        );

        assertEquals(procedureResult, queryEvaluator.evaluateQuery(singleWithQuery));
    }

    @Test
    void evaluateQuery_SingleSuchThat_Evaluated() throws QueryEvaluatorException {

        singleSuchThatQuery = new Query(
                selectedResult,
                Collections.singletonList(suchThat),
                Collections.emptyList(),
                Collections.emptyList()
        );
        assertEquals(assignResult, queryEvaluator.evaluateQuery(singleSuchThatQuery));
    }
}
