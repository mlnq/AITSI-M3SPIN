package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.QueryEvaluatorException;
import aitsi.m3spin.query.model.*;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.result.BooleanResult;
import aitsi.m3spin.query.model.result.QueryResult;
import aitsi.m3spin.query.model.result.SelectedResult;

import java.util.*;
import java.util.stream.Collectors;

public class QueryEvaluator {
    private final Pkb pkb;
    private final TNodeDao TNodeDao;
    private final List<Synonym> synonymList;

    public QueryEvaluator(Pkb pkb, List<Synonym> synonymList) {
        this.pkb = pkb;
        this.TNodeDao = new TNodeDao(pkb);
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

        if (selectedResult instanceof BooleanResult) { //Select BOOLEAN
            for (PqlClause clause : queryClauses) {
                if (!clause.evaluate()) {
                    return QueryResult.ofBoolean(false);
                }
            }
            return QueryResult.ofBoolean(true);

        } else { // Select synonym | Select synonym.attr
            Synonym selectedSynonym = SelectedResult.extractSynonym(selectedResult);
            Set<TNode> result = TNodeDao.findAllByType(pkb.getAst().getRoot(), selectedSynonym.getType());

            for (PqlClause clause : queryClauses) {
                if (clause.usesSynonym(selectedSynonym))
                    result = clause.evaluate(result);
                else {
                    if (!clause.evaluate()) {
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

//    private Set<TNode> evaluateWithList(List<WithClause> withList) throws IncompatibleTypesComparisionException {//todo chyba do wywalenia
//        Set<TNode> result = new HashSet<>();
//        withList.forEach(withClause -> {
//            result.addAll(evaluateWithClause(withClause));
//        });
//        return result;
//    }


//    private Set<TNode> evaluateSuchThatList(List<SuchThat> suchThatList, Set<TNode> foundNodes, Synonym searched) { todo chyba do wywalenia
//        Set<TNode> result;
//        //suchThatList.forEach(suchThat -> {
//        result = evaluateSuchThat(suchThatList.get(0));
//        //});
//        return result;
//    }

    private Set<TNode> getNodesFor(RelationArgument relationArgument) {
        Set<TNode> result;
        if (relationArgument instanceof Synonym) {
            Synonym synonym = (Synonym) relationArgument;
            result = TNodeDao.findAllByType(pkb.getAst().getRoot(), synonym.getType());
        } else if (relationArgument instanceof Constant) {
            Constant constant = (Constant) relationArgument;
            result = TNodeDao.findAllConstants(constant.getValue(), pkb.getAst().getRoot());
        } else {
            SimpleEntityName simpleEntityName = (SimpleEntityName) relationArgument;
            result = TNodeDao.findAllByAttribute(pkb.getAst().getRoot(), simpleEntityName.getEntityName());
        }
        return result;
    }

    private Set<TNode> findNodesInRelation(TNode node, RelationEnum relation) {
        Set<TNode> result = new HashSet<>();
        switch (relation) {
            case FOLLOWS_T:
                pkb.getFollowsInterface().getFollowsT((Statement) node).forEach(result::add);
                break;
            case FOLLOWS:
                result.add(pkb.getFollowsInterface().getFollows((Statement) node));
                break;
            case PARENT_T:
                result = pkb.getParentInterface().getParentT((Statement) node);
                break;
            case PARENT:
                result.add(pkb.getParentInterface().getParent((Statement) node));
                break;
            case USES:
                if (((Synonym) node).getType().equals(EntityType.PROCEDURE)) {
                    result = pkb.getUsesInterface().getVarsUsedByProc((Procedure) node);
                } else {
                    result = pkb.getUsesInterface().getVarsUsedByStmt((Statement) node);
                }
                break;
            case MODIFIES:
                if (node instanceof Procedure) {
                    result = pkb.getModifiesInterface().getModified((Procedure) node);
                } else {
                    result = pkb.getModifiesInterface().getModified((Statement) node);
                }
                break;
        }
        return result;
    }


}
