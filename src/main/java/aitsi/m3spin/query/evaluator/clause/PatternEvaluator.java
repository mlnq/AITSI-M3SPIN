package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.clauses.PqlClause;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
public class PatternEvaluator extends ClauseEvaluator {
    public PatternEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }

    @Override
    public Set<TNode> evaluateClause(Set<TNode> previousResult) {
        return Collections.emptySet();//todo przy pattenach
    }
}
