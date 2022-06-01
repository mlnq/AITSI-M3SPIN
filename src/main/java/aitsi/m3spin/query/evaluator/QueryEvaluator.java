package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.query.evaluator.clause.ClauseEvaluator;
import aitsi.m3spin.query.evaluator.clause.ClauseEvaluatorFactory;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.QueryEvaluatorException;
import aitsi.m3spin.query.model.Query;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.references.PrimitiveTypeReference;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.result.actual.BooleanResult;
import aitsi.m3spin.query.model.result.actual.QueryResult;
import aitsi.m3spin.query.model.result.reference.SelectedResult;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QueryEvaluator {
    private final Pkb pkb;
    private final TNodeDao tNodeDao;

    public QueryEvaluator(Pkb pkb) {
        this.pkb = pkb;
        this.tNodeDao = new TNodeDao(pkb);
    }

    public List<QueryResult> evaluateQueries(List<Query> queryList) throws QueryEvaluatorException {
        List<QueryResult> results = new ArrayList<>();
        for (Query query : queryList) {
            results.add(evaluateQuery(query));
        }
        return results;
    }

    public QueryResult evaluateQuery(Query query) throws QueryEvaluatorException {//todo ATS-5 rozbuć na mniejsze
        SelectedResult selectedResult = query.getSelectedResult();

        List<PqlClause> queryClauses = query.getAllClauses();
        ClauseEvaluatorFactory clauseEvaluatorFactory = new ClauseEvaluatorFactory(pkb, tNodeDao);

        if (selectedResult instanceof BooleanResult) { //Select BOOLEAN
            for (PqlClause clause : queryClauses) {
                ClauseEvaluator clauseEvaluator = clauseEvaluatorFactory.forClause(clause);
                if (!clauseEvaluator.evaluateBooleanClause(selectedResult).get()) {
                    return QueryResult.ofBoolean(false);
                }
            }
            return QueryResult.ofBoolean(true);

        } else { // Select synonym | Select synonym.attr
            Synonym selectedSynonym = selectedResult.getSynonym();
            TNodeSetResult result = new TNodeSetResult(tNodeDao.findAllByType(selectedSynonym.getSynonymType()));

            for (PqlClause clause : queryClauses) {
                ClauseEvaluator clauseEvaluator = clauseEvaluatorFactory.forClause(clause);
                if (clause.usesSynonym(selectedSynonym))
                    result = (TNodeSetResult) clauseEvaluator.evaluateClause(result, selectedResult);
                else {
                    if (!clauseEvaluator.evaluateBooleanClause(selectedResult).get()) {
                        return QueryResult.ofTNodeSet(Collections.emptySet());
                    }
                }
            }

            if (selectedResult instanceof Synonym) return QueryResult.ofTNodeSet(result.getResult());
            else return QueryResult.ofAttrList(
                    result.getResult().stream()
                            .map(AttributableNode.class::cast)
                            .map(attributableNode -> (PrimitiveTypeReference) attributableNode.getAttribute())
                            .collect(Collectors.toList()));
        }
    }


}
