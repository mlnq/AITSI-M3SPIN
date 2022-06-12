package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.QueryProcessorException;
import aitsi.m3spin.query.evaluator.clause.relationship.RelationEvaluator;
import aitsi.m3spin.query.evaluator.clause.relationship.RelationshipEvaluationStrategy;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.UnknownReferenceType;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.enums.RelationshipEvaluatorEnum;
import aitsi.m3spin.query.model.references.PrimitiveTypeReference;
import aitsi.m3spin.query.model.references.ReferenceType;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
public class SuchThatEvaluator extends ClauseEvaluator {
    private final SuchThat suchThat;

    public SuchThatEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
        suchThat = (SuchThat) pqlClause;
    }


    @Override
    public TNodeSetResult[] evaluateClause() throws QueryProcessorException {

        Set<? extends TNode> firstArgumentNodes = getNodesFor(suchThat.getFirstArgument());
        Set<? extends TNode> secondArgumentNodes = getNodesFor(suchThat.getSecondArgument());
        RelationshipEvaluatorEnum relation = suchThat.getEvaluatorRelationship();

        return evaluateRelationship(relation, firstArgumentNodes, secondArgumentNodes);
    }

    private Set<? extends TNode> getNodesFor(RelationshipArgumentRef relationshipArgumentRef) throws UnknownReferenceType {
        switch (relationshipArgumentRef.getArgRefType()) {
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
                throw new UnknownReferenceType(pqlClause, relationshipArgumentRef.getArgRefType());
        }
    }

    private TNodeSetResult[] evaluateRelationship(RelationshipEvaluatorEnum relationship, Set<? extends TNode> firstArgNodes,
                                                  Set<? extends TNode> secondArgNodes) {

        Set<TNode> secondResult = new HashSet<>();

        Set<TNode> firstResult = firstArgNodes.stream()
                .filter(node -> secondResult.addAll(filterMatchingNodesFromSet(relationship, node, secondArgNodes)))
                .collect(Collectors.toSet());

        return new TNodeSetResult[]{new TNodeSetResult(firstResult), new TNodeSetResult(secondResult)};
    }

    private Set<? extends TNode> filterMatchingNodesFromSet(RelationshipEvaluatorEnum relationship,
                                                            TNode firstArgNode, Set<? extends TNode> secondArgSet) {
        return secondArgSet.stream()
                .filter(secondArgNode -> checkIfRelationshipHoldsForNodes(relationship, firstArgNode, secondArgNode))
                .collect(Collectors.toSet());
    }

    private boolean checkIfRelationshipHoldsForNodes(RelationshipEvaluatorEnum relationship, TNode firstNode, TNode secondNode) {
        RelationEvaluator relationEvaluator = new RelationEvaluator(pkb);
        RelationshipEvaluationStrategy strategy = RelationEvaluator.chooseEvaluationStrategy(relationship);
        relationEvaluator.setStrategy(strategy);
        try {
            return relationEvaluator.evaluateRelationshipForNode(firstNode, secondNode);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public TNodeSetResult chooseResult(TNodeSetResult[] bothResults, Synonym selectedSynonym, TNodeSetResult selectedNodes) {
        RelationshipArgumentRef firstArg = suchThat.getFirstArgument();
        RelationshipArgumentRef secondArg = suchThat.getSecondArgument();

        if (firstArg.getArgRefType().equals(ReferenceType.SYNONYM)) {
            Synonym firstSynonym = (Synonym) firstArg;
            if (selectedSynonym.equalsToSynonym(firstSynonym)) return bothResults[0];
        }
        if (secondArg.getArgRefType().equals(ReferenceType.SYNONYM)) {
            Synonym secondSynonym = (Synonym) secondArg;
            if (selectedSynonym.equalsToSynonym(secondSynonym)) return bothResults[1];
        }

        return selectedNodes;
    }
}
