package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.clause.ClauseEvaluator;
import aitsi.m3spin.query.evaluator.clause.ClauseEvaluatorFactory;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.QueryEvaluatorException;
import aitsi.m3spin.query.model.Query;
import aitsi.m3spin.query.model.Synonym;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.result.BooleanResult;
import aitsi.m3spin.query.model.result.QueryResult;
import aitsi.m3spin.query.model.result.SelectedResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryEvaluator {
    private final Pkb pkb;
    private final TNodeDao tNodeDao;
    private final List<Synonym> synonymList;

    public QueryEvaluator(Pkb pkb, List<Synonym> synonymList) {
        this.pkb = pkb;
        this.tNodeDao = new TNodeDao(pkb);
        this.synonymList = synonymList;
    }

    public List<QueryResult> evaluateQueries(List<Query> queryList) throws QueryEvaluatorException {
        List<QueryResult> results = new ArrayList<>();
        for (Query query : queryList) {
            results.add(evaluateQuery(query));
        }
        return results;
    }

    public QueryResult evaluateQuery(Query query) throws QueryEvaluatorException {//todo rozbuć na mniejsze
        SelectedResult selectedResult = query.getSelectedResult();

        List<PqlClause> queryClauses = query.getAllClauses();
        ClauseEvaluatorFactory clauseEvaluatorFactory = new ClauseEvaluatorFactory(pkb, tNodeDao);

        if (selectedResult instanceof BooleanResult) { //Select BOOLEAN
            for (PqlClause clause : queryClauses) {
                ClauseEvaluator clauseEvaluator = clauseEvaluatorFactory.forClause(clause);
                if (!clauseEvaluator.evaluateBooleanClause()) {
                    return QueryResult.ofBoolean(false);
                }
            }
            return QueryResult.ofBoolean(true);

        } else { // Select synonym | Select synonym.attr
            Synonym selectedSynonym = SelectedResult.extractSynonym(selectedResult);
            Set<TNode> result = tNodeDao.findAllByType(pkb.getAst().getRoot(), selectedSynonym.getSynonymType());

            for (PqlClause clause : queryClauses) {
                ClauseEvaluator clauseEvaluator = clauseEvaluatorFactory.forClause(clause);
                if (clause.usesSynonym(selectedSynonym))
                    result = clauseEvaluator.evaluateClause(result);
                else {
                    if (!clauseEvaluator.evaluateBooleanClause()) {
                        return QueryResult.ofTNodeSet(Collections.emptySet());
                    }
                }
            }

            if (selectedResult instanceof Synonym) return QueryResult.ofTNodeSet(result);
            else return QueryResult.ofAttrList(
                    result.stream()
                            .map(TNode::getAttribute)
                            .collect(Collectors.toList()));
        }
    }

//    private Set<TNode> findNodesInRelation(TNode node, RelationEnum relation) {//todo
//        Set<TNode> result = new HashSet<>();
//        switch (relation) {
//            case FOLLOWS_T:
//                pkb.getFollowsInterface().getFollowsT((Statement) node).forEach(result::add);
//                break;
//            case FOLLOWS:
//                result.add(pkb.getFollowsInterface().getFollows((Statement) node));
//                break;
//            case PARENT_T:
//                result = pkb.getParentInterface().getParentT((Statement) node);
//                break;
//            case PARENT:
//                result.add(pkb.getParentInterface().getParent((Statement) node));
//                break;
//            case USES:
//                if (((Synonym) node).getSynonymType().equals(EntityType.PROCEDURE)) {
//                    result = pkb.getUsesInterface().getVarsUsedByProc((Procedure) node);
//                } else {
//                    result = pkb.getUsesInterface().getVarsUsedByStmt((Statement) node);
//                }
//                break;
//            case MODIFIES:
//                if (node instanceof Procedure) {
//                    result = pkb.getModifiesInterface().getModified((Procedure) node);
//                } else {
//                    result = pkb.getModifiesInterface().getModified((Statement) node);
//                }
//                break;
//        }
//        return result;
//    }


}
