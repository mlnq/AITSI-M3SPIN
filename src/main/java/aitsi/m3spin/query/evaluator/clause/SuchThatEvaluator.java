package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.UnknownReferenceType;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.enums.RelationEnum;
import aitsi.m3spin.query.model.references.PrimitiveTypeReference;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import aitsi.m3spin.query.model.result.QueryResult;
import aitsi.m3spin.query.model.result.SelectedResult;
import aitsi.m3spin.query.model.result.TNodeSetResult;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
public class SuchThatEvaluator extends ClauseEvaluator {
    public SuchThatEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }


    @Override
    public QueryResult evaluateClause(TNodeSetResult previousResult, SelectedResult selectedResult) throws UnknownReferenceType {
        SuchThat suchThat = (SuchThat) pqlClause;

        Set<? extends TNode> firstArgumentNodes = getNodesFor(suchThat.getFirstArgument());
        Set<? extends TNode> secondArgumentNodes = getNodesFor(suchThat.getSecondArgument());

        RelationEnum relation = suchThat.getRelation();

        return evaluateRelationship(relation, firstArgumentNodes, secondArgumentNodes);
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

    private QueryResult evaluateRelationship(RelationEnum relationship,
                                             Set<? extends TNode> firstArgNodes, Set<? extends TNode> secondArgNodes) {

        return new TNodeSetResult(firstArgNodes.stream()
                .filter(node -> checkIfRelationshipHoldsForNode(relationship, node, secondArgNodes))
                .collect(Collectors.toSet()));
    }

    private boolean checkIfRelationshipHoldsForNode(RelationEnum relationship, TNode firstNode, Set<? extends TNode> nodeSet) {
        return
                nodeSet.stream()
                        .anyMatch(secondNode -> {
                            switch (relationship) {
                                case FOLLOWS_T:
                                    List<Statement> actualStmtsWhichFollowFirst = pkb.getFollowsInterface().getFollowsT((Statement) firstNode);
                                    return actualStmtsWhichFollowFirst.contains(secondNode);//todo all sus calls ATS-5
                                case FOLLOWS:
                                    Statement actualStmtWhichFollowsFirst = pkb.getFollowsInterface().getFollows((Statement) firstNode);
                                    return secondNode.equals(actualStmtWhichFollowsFirst);
                                case PARENT_T:
                                    List<Statement> stmtsParentedByFirstT = pkb.getParentInterface().getParentedByT((Statement) firstNode);
                                    return stmtsParentedByFirstT.contains(secondNode);
                                case PARENT:
                                    List<Statement> stmtsParentedByFirst = pkb.getParentInterface().getParentedBy((Statement) firstNode);
                                    return stmtsParentedByFirst.contains(secondNode);
                                case USES:
                                    if (EntityType.PROCEDURE.equals(firstNode.getType())) {
                                        Set<Variable> varsUsedByProc = pkb.getUsesInterface().getVarsUsedByProc((Procedure) firstNode);
                                        return varsUsedByProc.contains(secondNode);
                                    } else {
                                        Set<Variable> varsUsedByStmt = pkb.getUsesInterface().getVarsUsedByStmt((Statement) firstNode);
                                        return varsUsedByStmt.contains(secondNode);
                                    }
                                case MODIFIES:
                                    if (EntityType.PROCEDURE.equals(firstNode.getType())) {
                                        Set<Variable> varsModifiedByProc = pkb.getModifiesInterface().getModified((Procedure) firstNode);
                                        return varsModifiedByProc.contains(secondNode);
                                    } else {
                                        Set<Variable> varsUsedByStmt = pkb.getUsesInterface().getVarsUsedByStmt((Statement) firstNode);
                                        return varsUsedByStmt.contains(secondNode);
                                    }
                                default:
                                    return false;
                            }
                        });
    }
}
