package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisionException;
import aitsi.m3spin.query.model.clauses.PqlClause;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public abstract class ClauseEvaluator {
    protected final Pkb pkb;
    protected final TNodeDao tNodeDao;
    @Setter
    protected PqlClause pqlClause;

    public boolean evaluateBooleanClause(){
        return evaluateBooleanClause(Collections.emptySet());
    }
    public boolean evaluateBooleanClause(Set<TNode> previousResult){
        return !evaluateClause(previousResult).isEmpty();
    }

    public Set<TNode> evaluateClause(){
        return evaluateClause(Collections.emptySet());
    }

    public abstract Set<TNode> evaluateClause(Set<TNode> previousResult) throws IncompatibleTypesComparisionException;
}
