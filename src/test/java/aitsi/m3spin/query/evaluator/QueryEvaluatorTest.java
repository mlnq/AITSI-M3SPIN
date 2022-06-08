package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.query.QueryProcessorException;
import aitsi.m3spin.query.model.Query;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.enums.AttributeEnum;
import aitsi.m3spin.query.model.references.AttributeReference;
import aitsi.m3spin.query.model.references.StringReference;
import aitsi.m3spin.query.model.references.WithArgumentRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryEvaluatorTest extends QueryTestingData {
    private QueryEvaluator queryEvaluator;


    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();

        queryEvaluator = new QueryEvaluator(pkb);
    }

    @Test
    void evaluateQueries() {
        //todo test metody ATS-16
    }

    @Test
    void evaluateQuery_SingleWithClause_Evaluated() throws QueryProcessorException {
        WithArgumentRef leftRef = new AttributeReference(procSynonym, AttributeEnum.PROC_NAME);
        WithArgumentRef rightRef = new StringReference(PROCEDURE_NAME);
        WithClause withClause = new WithClause(leftRef, rightRef);

        Query singleWithQuery = new Query(
                selectedProcSynonym,
                Collections.singletonList(withClause)
        );

        assertEquals(procedureResult, queryEvaluator.evaluateQuery(singleWithQuery));
    }

    @Test
    void evaluateQuery_SingleSuchThat_Evaluated() throws QueryProcessorException {

        Query singleSuchThatQuery = new Query(
                selectedProcSynonym,
                Collections.singletonList(followsSuchThat)
        );
        assertEquals(procedureResult, queryEvaluator.evaluateQuery(singleSuchThatQuery));
    }
}
