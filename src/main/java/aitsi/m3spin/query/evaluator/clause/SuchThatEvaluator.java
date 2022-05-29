package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.clause.relationship.RelationEvaluator;
import aitsi.m3spin.query.evaluator.clause.relationship.RelationshipEvaluationStrategy;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.NoSynonymInSelectedResultException;
import aitsi.m3spin.query.evaluator.exception.QueryEvaluatorException;
import aitsi.m3spin.query.evaluator.exception.UnknownReferenceType;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.enums.RelationshipEnum;
import aitsi.m3spin.query.model.references.PrimitiveTypeReference;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import aitsi.m3spin.query.model.result.actual.QueryResult;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import aitsi.m3spin.query.model.result.reference.SelectedResult;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
public class SuchThatEvaluator extends ClauseEvaluator {
    public SuchThatEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }


    @Override
    public QueryResult evaluateClause(TNodeSetResult previousResult, SelectedResult selectedResult) throws QueryEvaluatorException {
        SuchThat suchThat = (SuchThat) pqlClause;

        Set<? extends TNode> firstArgumentNodes = getNodesFor(suchThat.getFirstArgument());
        Set<? extends TNode> secondArgumentNodes = getNodesFor(suchThat.getSecondArgument());
        RelationshipEnum relation = suchThat.getRelation();

        QueryResult[] bothResults = evaluateRelationship(relation, firstArgumentNodes, secondArgumentNodes);

        return chooseResult(bothResults, selectedResult, suchThat);
    }

    private QueryResult chooseResult(QueryResult[] bothResults, SelectedResult selectedResult, SuchThat suchThat) throws NoSynonymInSelectedResultException {
        Synonym firstSynonym = (Synonym) suchThat.getFirstArgument();
        Synonym secondSynonym = (Synonym) suchThat.getSecondArgument();

        if (selectedResult.getSynonym().equalsToSynonym(firstSynonym)) return bothResults[0];
        else return bothResults[1];
    }

    private Set<? extends TNode> getNodesFor(RelationshipArgumentRef relationshipArgumentRef) throws UnknownReferenceType {
        switch (relationshipArgumentRef.getReferenceType()) {
            case STRING:
            case INTEGER:
                PrimitiveTypeReference primitiveReference = ((PrimitiveTypeReference) relationshipArgumentRef);
                return tNodeDao.findAllByAttribute(primitiveReference.getAsAttribute());
            case SYNONYM:
                Synonym synonym = (Synonym) relationshipArgumentRef;
                return tNodeDao.findAllByType(synonym.getSynonymType());
            case WILDCARD:
                return tNodeDao.findAll();
            default:
                throw new UnknownReferenceType(pqlClause, relationshipArgumentRef.getReferenceType());
        }
    }

    private QueryResult[] evaluateRelationship(RelationshipEnum relationship, Set<? extends TNode> firstArgNodes,
                                               Set<? extends TNode> secondArgNodes) {

        Set<TNode> firstResult = new HashSet<TNode>();
        Set<TNode> secondResult = new HashSet<TNode>();

        firstResult.addAll(
                firstArgNodes.stream()
                        .filter(node -> secondResult.addAll(filterMatchingNodesFromSet(relationship, node, secondArgNodes)))
                        .collect(Collectors.toSet()));

        return new QueryResult[]{QueryResult.ofTNodeSet(firstResult), QueryResult.ofTNodeSet(secondResult)};
    }

    private Set<? extends TNode> filterMatchingNodesFromSet(RelationshipEnum relationship,
                                                            TNode firstArgNode, Set<? extends TNode> secondArgSet) {
        return secondArgSet.stream()
                .filter(secondArgNode -> checkIfRelationshipHoldsForNodes(relationship, firstArgNode, secondArgNode))
                .collect(Collectors.toSet());
    }

    private boolean checkIfRelationshipHoldsForNodes(RelationshipEnum relationship, TNode firstNode, TNode secondNode) {
        RelationEvaluator relationEvaluator = new RelationEvaluator(pkb);
        RelationshipEvaluationStrategy strategy = RelationEvaluator.chooseEvaluationStrategy(relationship);
        relationEvaluator.setStrategy(strategy);
        try {
            return relationEvaluator.evaluateRelationshipForNode(firstNode, secondNode);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
