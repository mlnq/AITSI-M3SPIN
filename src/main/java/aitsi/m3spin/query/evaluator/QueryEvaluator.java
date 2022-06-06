package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.query.QueryProcessorException;
import aitsi.m3spin.query.evaluator.clause.ClauseEvaluator;
import aitsi.m3spin.query.evaluator.clause.ClauseEvaluatorFactory;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.Query;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.references.PrimitiveTypeReference;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.result.actual.BooleanResult;
import aitsi.m3spin.query.model.result.actual.QueryResult;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import aitsi.m3spin.query.model.result.reference.SelectedResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryEvaluator {
    private final Pkb pkb;
    private final TNodeDao tNodeDao;

    public QueryEvaluator(Pkb pkb) {
        this.pkb = pkb;
        this.tNodeDao = new TNodeDao(pkb);
    }

    public List<QueryResult> evaluateQueries(List<Query> queryList) throws QueryProcessorException {
        List<QueryResult> results = new ArrayList<>();
        for (Query query : queryList) {
            results.add(evaluateQuery(query));
        }
        return results;
    }

    public QueryResult evaluateQuery(Query query) throws QueryProcessorException {
        SelectedResult selectedResult = query.getSelectedResult();

        List<PqlClause> queryClauses = query.getAllClauses();
        ClauseEvaluatorFactory clauseEvaluatorFactory = new ClauseEvaluatorFactory(pkb, tNodeDao);

        if (selectedResult instanceof BooleanResult) { //Select BOOLEAN
            return evaluateBooleanQuery(queryClauses, clauseEvaluatorFactory);

        } else { // Select synonym | Select synonym.attr
            Synonym selectedSynonym = selectedResult.getSynonym();
            Set<Synonym> selectedAndRelatedSynonyms = query.getRelatedSynonyms(selectedSynonym, Collections.singleton(selectedSynonym));
            return evaluateSynonymQuery(selectedResult, queryClauses, clauseEvaluatorFactory, selectedAndRelatedSynonyms);
        }
    }

    private QueryResult evaluateSynonymQuery(SelectedResult selectedResult, List<PqlClause> clauses, ClauseEvaluatorFactory clauseEvaluatorFactory,
                                             Set<Synonym> selectedAndRelatedSynonyms) throws QueryProcessorException {
        Synonym selectedSynonym = selectedResult.getSynonym();
        Set<? extends TNode> result = tNodeDao.findAllByType(selectedSynonym.getSynonymType());

        for (PqlClause clause : clauses) {
            ClauseEvaluator clauseEvaluator = clauseEvaluatorFactory.forClause(clause);
            if (clause.usesAnySynonym(selectedAndRelatedSynonyms)) { //tu tylko sprawsdza, czy to co w Select jest używane w klauzuli
                TNodeSetResult[] bothRes = clauseEvaluator.evaluateClause();
                TNodeSetResult chosenResult = clauseEvaluator.chooseResult(bothRes, selectedSynonym);
                result.retainAll(chosenResult.getResult());//todo do przetestowania ATS-16
            } else {
                if (!clauseEvaluator.evaluateBooleanClause().get()) {
                    return QueryResult.ofTNodeSet(Collections.emptySet());
                }
            }
        }

        if (selectedResult instanceof Synonym) return QueryResult.ofTNodeSet(result);
        else return QueryResult.ofAttrList(
                result.stream()
                        .map(AttributableNode.class::cast)
                        .map(attributableNode -> (PrimitiveTypeReference) attributableNode.getAttribute())
                        .collect(Collectors.toList()));
    }

    private QueryResult evaluateBooleanQuery(List<PqlClause> queryClauses, ClauseEvaluatorFactory clauseEvaluatorFactory) throws QueryProcessorException {
        for (PqlClause clause : queryClauses) {
            ClauseEvaluator clauseEvaluator = clauseEvaluatorFactory.forClause(clause);
            if (!clauseEvaluator.evaluateBooleanClause().get()) {
                return QueryResult.ofBoolean(false);
            }
        }
        return QueryResult.ofBoolean(true);
    }
}
