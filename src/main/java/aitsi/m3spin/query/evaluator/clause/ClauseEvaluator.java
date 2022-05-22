package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisionException;
import aitsi.m3spin.query.evaluator.exception.UnknownPqlClauseException;
import aitsi.m3spin.query.model.clauses.Pattern;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.clauses.WithClause;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public abstract class ClauseEvaluator {
    protected final Pkb pkb;
    protected final TNodeDao tNodeDao;
    protected final PqlClause pqlClause;

    public static ClauseEvaluator forClause(PqlClause pqlClause, Pkb pkb, TNodeDao tNodeDao) throws UnknownPqlClauseException {

        if (pqlClause instanceof SuchThat) return new SuchThatEvaluator(pkb, tNodeDao, pqlClause);
        else if (pqlClause instanceof WithClause) return new SuchThatEvaluator(pkb, tNodeDao, pqlClause);
        else if (pqlClause instanceof Pattern) return new PatternEvaluator(pkb, tNodeDao, pqlClause);
        else throw new UnknownPqlClauseException(pqlClause);
    }


    public boolean evaluateBooleanClause() throws IncompatibleTypesComparisionException {
        return evaluateBooleanClause(Collections.emptySet());
    }

    public boolean evaluateBooleanClause(Set<TNode> previousResult) throws IncompatibleTypesComparisionException {
        return !evaluateClause(previousResult).isEmpty();
    }

    public Set<TNode> evaluateClause() throws IncompatibleTypesComparisionException {
        return evaluateClause(Collections.emptySet());
    }

    public abstract Set<TNode> evaluateClause(Set<TNode> previousResult) throws IncompatibleTypesComparisionException;
}
