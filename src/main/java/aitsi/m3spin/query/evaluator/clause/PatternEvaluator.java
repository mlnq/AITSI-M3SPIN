package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class PatternEvaluator extends ClauseEvaluator {
    public PatternEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }

    @Override
    public TNodeSetResult[] evaluateClause() {
        return new TNodeSetResult[0];//todo po 1 iteracji
    }

    @Override
    public TNodeSetResult chooseResult(TNodeSetResult[] bothResults, Synonym selectedSynonym, TNodeSetResult selectedNodes) {
        return null;//todo po 1 iteracji
    }
}
