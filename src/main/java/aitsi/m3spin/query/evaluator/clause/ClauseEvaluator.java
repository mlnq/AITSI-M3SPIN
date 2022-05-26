package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.evaluator.exception.UnknownPqlClauseException;
import aitsi.m3spin.query.model.clauses.Pattern;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.result.BooleanResult;
import aitsi.m3spin.query.model.result.QueryResult;
import aitsi.m3spin.query.model.result.SelectedResult;
import aitsi.m3spin.query.model.result.TNodeSetResult;
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


    public BooleanResult evaluateBooleanClause(SelectedResult selectedResult) throws IncompatibleTypesComparisonException {
        return evaluateBooleanClause(Collections.emptySet(), selectedResult);
    }

    public BooleanResult evaluateBooleanClause(Set<TNode> previousResult, SelectedResult selectedResult) throws IncompatibleTypesComparisonException {
        return (BooleanResult) evaluateClause(new TNodeSetResult(previousResult), selectedResult);
    }

    public QueryResult evaluateClause(SelectedResult selectedResult) throws IncompatibleTypesComparisonException {
        return evaluateClause(new TNodeSetResult(Collections.emptySet()), selectedResult);
    }

    public abstract QueryResult evaluateClause(TNodeSetResult previousResult, SelectedResult selectedResult) throws IncompatibleTypesComparisonException;
}
