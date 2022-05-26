package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.result.QueryResult;
import aitsi.m3spin.query.model.result.SelectedResult;
import aitsi.m3spin.query.model.result.TNodeSetResult;
import lombok.EqualsAndHashCode;

import java.util.Collections;

@EqualsAndHashCode(callSuper = false)
public class PatternEvaluator extends ClauseEvaluator {
    public PatternEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }

    @Override
    public QueryResult evaluateClause(TNodeSetResult previousResult, SelectedResult selectedResult) {
        return new TNodeSetResult(Collections.emptySet());//todo przy pattenach
    }
}
