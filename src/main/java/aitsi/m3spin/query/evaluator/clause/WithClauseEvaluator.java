package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.references.*;
import aitsi.m3spin.query.model.result.BooleanResult;
import aitsi.m3spin.query.model.result.QueryResult;
import aitsi.m3spin.query.model.result.SelectedResult;
import aitsi.m3spin.query.model.result.TNodeSetResult;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
public class WithClauseEvaluator extends ClauseEvaluator {

    public WithClauseEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }

    @Override//todo rozbić tego kolosa ATS-5
    public QueryResult evaluateClause(TNodeSetResult previousResult, SelectedResult selectedResult) throws IncompatibleTypesComparisonException {
        WithClause withClause = (WithClause) pqlClause;

        ReferenceType leftHandType = withClause.getLeftHandReference().getReferenceType();
        ReferenceType rightHandType = withClause.getRightHandReference().getReferenceType();
        if (!leftHandType.equals(rightHandType))//todo ten if  do preprocessora
            throw new IncompatibleTypesComparisonException(leftHandType, rightHandType);

        WithArgumentRef leftHandRef = withClause.getLeftHandReference();
        WithArgumentRef rightHandRef = withClause.getRightHandReference();

        if (leftHandRef.isConstantValue() && rightHandRef.isConstantValue()) {
            if (((PrimitiveTypeReference) leftHandRef).getValue()
                    .equals(((PrimitiveTypeReference) rightHandRef).getValue()))
                return new BooleanResult(true);
            else return new BooleanResult(false);

        } else if (leftHandRef.isConstantValue() ^ rightHandRef.isConstantValue()) {
            final PrimitiveTypeReference[] primitiveRef = new PrimitiveTypeReference[1];
            final AttributeReference[] attributeRef = new AttributeReference[1];
            Arrays.stream(withClause.getBothReferences())
                    .forEach(reference -> {
                        if (reference.isConstantValue()) primitiveRef[0] = (PrimitiveTypeReference) reference;
                        else attributeRef[0] = (AttributeReference) reference;
                    });

            Set<TNode> tNodes = tNodeDao.findAllByType(attributeRef[0].getSynonym().getSynonymType());

            return new TNodeSetResult(tNodes);

        } else {
            Set<AttributableNode> leftResult = tNodeDao.findAllByType(
                            ((ComplexTypeReference) leftHandRef).getSynonym().getSynonymType()
                    ).stream()
                    .map(AttributableNode.class::cast)
                    .collect(Collectors.toSet());

            Set<AttributableNode> rightResult = tNodeDao.findAllByType(
                            ((ComplexTypeReference) rightHandRef).getSynonym().getSynonymType()).stream()
                    .map(AttributableNode.class::cast)
                    .collect(Collectors.toSet());

            leftResult = leftResult.stream()
                    .filter(node -> setHasNodeWithAttribute(rightResult, node))
                    .collect(Collectors.toSet());

            return new TNodeSetResult(leftResult);
        }
    }

    private boolean setHasNodeWithAttribute(Set<AttributableNode> nodeSet, AttributableNode node) {
        return nodeSet.stream()
                .map(AttributableNode::getAttribute)
                .anyMatch(nodeAttribute -> node.getAttribute().equals(nodeAttribute));
    }
}
