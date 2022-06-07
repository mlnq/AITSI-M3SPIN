package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.query.evaluator.QueryTestingData;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.enums.AttributeEnum;
import aitsi.m3spin.query.model.references.AttributeReference;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class WithClauseEvaluatorTest extends QueryTestingData {
    private WithClauseEvaluator withClauseEvaluator;
    private WithClause withClause;
    private TNodeDao tNodeDao;

    @BeforeEach
    @Override
    protected void setUp() {
        super.setUp();
        tNodeDao = new TNodeDao(pkb);
        withClause = new WithClause(
                new AttributeReference(varSynonym, AttributeEnum.VAR_NAME),
                new AttributeReference(procSynonym, AttributeEnum.PROC_NAME)
        );
    }

    @Test
    void EvaluateClause_TwoAttrRefsComparison_ReturnedEmptyResultArray() {
        withClauseEvaluator = new WithClauseEvaluator(pkb, tNodeDao, withClause);

        TNodeSetResult[] emptyRes = new TNodeSetResult[]{TNodeSetResult.empty(), TNodeSetResult.empty()};
        assertArrayEquals(emptyRes, withClauseEvaluator.evaluateClause());
    }
}
