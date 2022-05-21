package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.NodeDao;
import aitsi.m3spin.query.evaluator.exception.NoSuchAttributeException;
import aitsi.m3spin.query.evaluator.exception.QueryEvaluatorException;
import aitsi.m3spin.query.evaluator.exception.UnknownPqlClauseException;
import aitsi.m3spin.query.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class QueryEvaluator {
    private final Pkb pkb;
    private final NodeDao nodeDao;
    private final List<Synonym> synonymList;

    public QueryEvaluator(Pkb pkb, List<Synonym> synonymList) {
        this.pkb = pkb;
        this.nodeDao = new NodeDao(pkb);
        this.synonymList = synonymList;
    }

    public List<Set<TNode>> evaluateQueries(List<Query> queryList) throws QueryEvaluatorException {//todo zmienić typ zwracany na QueryResult
        List<Set<TNode>> results = new ArrayList<>();
        for (Query query : queryList) {
            results.add(evaluateQuery(query));
        }
        return results;
    }

    public QueryResult evaluateQuery(Query query) throws QueryEvaluatorException {
        SelectedResult selectedResult = query.getSelectedResult();

        List<PqlClause> queryClauses = query.getWithList().stream()
                .map(withClause -> (PqlClause) withClause)
                .collect(Collectors.toList());

        queryClauses.addAll(
                query.getSuchThatList().stream()
                        .map(suchThat -> (PqlClause) suchThat)
                        .collect(Collectors.toList())
        );

        if (selectedResult instanceof BooleanResult) { //Select BOOLEAN
            for (PqlClause clause : queryClauses) {
                if (!evaluateClause(clause)) {
                    return false;
                }
            }
            return true;
        } else { // Select synonym | Select synonym.attr
            Synonym selectedSynonym = SelectedResult.extractSynonym(selectedResult);
            Set<TNode> result = nodeDao.findAllByType(pkb.getAst().getRoot(), selectedSynonym.getType());

            for (PqlClause clause : queryClauses) {
                if (clause.usesSynonym(selectedSynonym))
                    result = evaluateClause(clause, result);
                else {
                    if (!evaluateClause(clause)) {
                        return Collections.emptySet();
                    }
                }
            }
            return result;
        }
    }

    private boolean evaluateClause(PqlClause clause) throws NoSuchAttributeException, UnknownPqlClauseException {
        if (clause instanceof WithClause) return !evaluateWithClause((WithClause) clause).isEmpty();
        else if (clause instanceof SuchThat) return !evaluateSuchThat((SuchThat) clause).isEmpty();
        else throw new UnknownPqlClauseException(clause);
    }

    private Set<TNode> evaluateClause(PqlClause clause, Set<TNode> result) {
        return null;
    }

//    private Set<TNode> evaluateWithList(List<WithClause> withList) throws NoSuchAttributeException {
//        Set<TNode> result = new HashSet<>();
//        withList.forEach(withClause -> {
//            result.addAll(evaluateWithClause(withClause));
//        });
//        return result;
//    }

    private Set<TNode> evaluateWithClause(WithClause withClause) throws NoSuchAttributeException {
        if (!withClause.getSynonym().getType().equals(withClause.getAttribute().getEntityType()))//todo ten if  do preprocessora
            throw new NoSuchAttributeException(withClause.getSynonym(), withClause.getAttribute());
        Set<TNode> nodes = nodeDao.findAllByType(pkb.getAst().getRoot(), withClause.getSynonym().getType());
        nodes.stream()
                .filter(node -> node.getAttribute().equals(withClause.getValue()));
        return nodes;
    }
    //TODO ogarnąć atrybuty - zadanie #20


    private Set<TNode> evaluateSuchThatList(List<SuchThat> suchThatList, Set<TNode> foundNodes, Synonym searched) {
        Set<TNode> result;
        //suchThatList.forEach(suchThat -> {
        result = evaluateSuchThat(suchThatList.get(0));
        //});
        return result;
    }

    private Set<TNode> evaluateSuchThat(SuchThat suchThat) {
        Set<TNode> result = new HashSet<>();
        Set<TNode> firstArgumentNodes = getNodesFor(suchThat.getFirstArgument());
        Set<TNode> secondArgumentNodes = getNodesFor(suchThat.getSecondArgument());

        RelationEnum relation = suchThat.getRelation();
        firstArgumentNodes.forEach(node -> {
            if (suchThat.getFirstArgument() instanceof Synonym)
                findNodesInRelation(node, relation);
        });
        return result;
    }

    private Set<TNode> getNodesFor(RelationArgument relationArgument) {
        Set<TNode> result = new HashSet<>();
        if (relationArgument instanceof Synonym) {
            Synonym synonym = (Synonym) relationArgument;
            result = nodeDao.findAllByType(pkb.getAst().getRoot(), synonym.getType());
        } else if (relationArgument instanceof Constant) {
            Constant constant = (Constant) relationArgument;
            result = nodeDao.findAllConstants(constant.getValue(), pkb.getAst().getRoot());
        } else {
            SimpleEntityName simpleEntityName = (SimpleEntityName) relationArgument;
            result = nodeDao.findAllByAttribute(pkb.getAst().getRoot(), simpleEntityName.getEntityName());
        }
        return result;
    }

    private Set<TNode> findNodesInRelation(TNode node, RelationEnum relation) {
        Set<TNode> result = new HashSet<>();
        switch (relation) {
            case FOLLOWS_T:
                pkb.getFollowsInterface().getFollowsT((Statement) node).forEach(res -> result.add(res));
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
