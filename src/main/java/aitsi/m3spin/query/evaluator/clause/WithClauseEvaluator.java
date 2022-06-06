package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.pkb.model.AttributableNode;
import aitsi.m3spin.pkb.model.IntegerAttribute;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.enums.WithArgRefType;
import aitsi.m3spin.query.model.references.*;
import aitsi.m3spin.query.model.result.actual.BooleanResult;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.stream.Collectors;

import static aitsi.m3spin.query.model.enums.WithArgRefType.*;

@EqualsAndHashCode(callSuper = false)
public class WithClauseEvaluator extends ClauseEvaluator {
    private final WithClause withClause;
    private final WithArgumentRef leftHandRef;
    private final WithArgumentRef rightHandRef;
    private final WithArgRefType rightHandType;
    private final WithArgRefType leftHandType;

    public WithClauseEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
        withClause = (WithClause) pqlClause;

        leftHandRef = withClause.getLeftHandReference();
        rightHandRef = withClause.getRightHandReference();
        leftHandType = leftHandRef.getWithArgRefType();
        rightHandType = rightHandRef.getWithArgRefType();
    }

    @Override//todo trochę zbyt skomplikowana metoda, ale na razie zostawiam, bo nie mam pomysłu jak rozbić. ATS-27
    public TNodeSetResult[] evaluateClause() {
        if (leftHandType.equals(ATTR_REF) && rightHandType.equals(ATTR_REF)) {
            return compareTwoAttrRefs();
        } else if (leftHandType.equals(PROG_LINE) && rightHandType.equals(PROG_LINE)) {
            return compareTwoProgLines();

        } else if (PRIMITIVE.equals(leftHandType) && ATTR_REF.equals(rightHandType)) {
            return compareAttrRefWithPrimitive((AttributeReference) rightHandRef, (PrimitiveTypeReference) leftHandRef);
        } else if (PRIMITIVE.equals(rightHandType) && ATTR_REF.equals(leftHandType)) {
            return compareAttrRefWithPrimitive((AttributeReference) leftHandRef, (PrimitiveTypeReference) rightHandRef);

        } else if (ATTR_REF.equals(leftHandType) && PROG_LINE.equals(rightHandType)) {
            return compareAttrRefWithProgLine((AttributeReference) leftHandRef);
        } else if (ATTR_REF.equals(rightHandType) && PROG_LINE.equals(leftHandType)) {
            return compareAttrRefWithProgLine((AttributeReference) rightHandRef);

        } else if (PRIMITIVE.equals(leftHandType) && PROG_LINE.equals(rightHandType)) {
            return comparePrimitiveWithProgLine((PrimitiveTypeReference) leftHandRef);
        } else if (PRIMITIVE.equals(rightHandType) && PROG_LINE.equals(leftHandType)) {
            return comparePrimitiveWithProgLine((PrimitiveTypeReference) rightHandRef);
        } else return new TNodeSetResult[0];
    }

    @Override
    public BooleanResult evaluateBooleanClause() {
        if (PRIMITIVE.equals(leftHandType) && PRIMITIVE.equals(rightHandType)) {
            boolean comparisonResult = ((PrimitiveTypeReference) leftHandRef).getValue()
                    .equals(((PrimitiveTypeReference) rightHandRef).getValue());

            return new BooleanResult(comparisonResult);
        } else {
            return new BooleanResult(false);
            /*
            ATS-16
            ^ todo do rozkminy: czy może być sytuacja, że synonim nie jest używany ale porównywane nie będą 2 prymitywy
            // co tu zwrócić
             */
        }
    }

    private TNodeSetResult[] compareAttrRefWithProgLine(AttributeReference typeAttr) {
        Set<AttributableNode> attributableNodes = tNodeDao.findAllByType(typeAttr.getSynonym().getSynonymType()).stream()
                .map(AttributableNode.class::cast)
                .collect(Collectors.toSet());

        Set<AttributableNode> statements = tNodeDao.findAllStmts().stream()
                .map(AttributableNode.class::cast)
                .collect(Collectors.toSet());

        Set<AttributableNode> finalAttributableNodes = attributableNodes;
        statements = statements.stream()
                .filter(node -> setHasNodeWithAttribute(finalAttributableNodes, node))
                .collect(Collectors.toSet());

        Set<AttributableNode> finalStatements = statements;
        attributableNodes = attributableNodes.stream()
                .filter(node -> setHasNodeWithAttribute(finalStatements, node))
                .collect(Collectors.toSet());

        return new TNodeSetResult[]{new TNodeSetResult(statements), new TNodeSetResult(attributableNodes)};
    }

    private TNodeSetResult[] comparePrimitiveWithProgLine(PrimitiveTypeReference primitiveTypeReference) {
        Set<TNode> allByProgLine = tNodeDao.findAllByProgLine((IntegerAttribute) primitiveTypeReference.getAsAttribute());
        return new TNodeSetResult[]{new TNodeSetResult(allByProgLine)};
    }

    private TNodeSetResult[] compareTwoProgLines() {
        Set<TNode> allStmts = tNodeDao.findAllStmts();

        return new TNodeSetResult[]{new TNodeSetResult(allStmts), new TNodeSetResult(allStmts)};
    }


    private TNodeSetResult[] compareTwoAttrRefs() {
        Set<AttributableNode> leftResult = tNodeDao.findAllByType(
                        ((ComplexTypeReference) leftHandRef).getSynonym().getSynonymType()
                ).stream()
                .map(AttributableNode.class::cast)
                .collect(Collectors.toSet());

        Set<AttributableNode> rightResult = tNodeDao.findAllByType(
                        ((ComplexTypeReference) rightHandRef).getSynonym().getSynonymType()).stream()
                .map(AttributableNode.class::cast)
                .collect(Collectors.toSet());

        Set<AttributableNode> finalRightResult = rightResult;
        leftResult = leftResult.stream()
                .filter(node -> setHasNodeWithAttribute(finalRightResult, node))
                .collect(Collectors.toSet());

        Set<AttributableNode> finalLeftResult = leftResult;
        rightResult = rightResult.stream()
                .filter(node -> setHasNodeWithAttribute(finalLeftResult, node))
                .collect(Collectors.toSet());

        return new TNodeSetResult[]{new TNodeSetResult(leftResult), new TNodeSetResult(rightResult)};
    }

//    private boolean checkTypes(WithArgRefType type1, WithArgRefType type2, WithArgumentRef[] bothReferences) {
//        return (type1.equals(bothReferences[0].getWithArgRefType()) && type2.equals(bothReferences[1].getWithArgRefType())) ||
//                type1.equals(bothReferences[1].getWithArgRefType()) && type2.equals(bothReferences[0].getWithArgRefType());
//    }

    private TNodeSetResult[] compareAttrRefWithPrimitive(AttributeReference attributeReference, PrimitiveTypeReference primitiveTypeReference) {
        Set<TNode> tNodes = tNodeDao.findAllByType(attributeReference.getSynonym().getSynonymType());
        tNodes = tNodes.stream()
                .map(AttributableNode.class::cast)
                .filter(attributableNode -> attributableNode.getAttribute().equals(primitiveTypeReference.getAsAttribute()))
                .collect(Collectors.toSet());

        return new TNodeSetResult[]{new TNodeSetResult(tNodes), TNodeSetResult.empty()};
    }

    private boolean setHasNodeWithAttribute(Set<AttributableNode> nodeSet, AttributableNode node) {
        return nodeSet.stream()
                .map(AttributableNode::getAttribute)
                .anyMatch(nodeAttribute -> node.getAttribute().equals(nodeAttribute));
    }

    @Override
    public TNodeSetResult chooseResult(TNodeSetResult[] bothResults, Synonym selectedSynonym) {
        if (withRefContainsSelectedSynonym(leftHandType, leftHandRef, selectedSynonym))
            return bothResults[0];

        if (withRefContainsSelectedSynonym(rightHandType, rightHandRef, selectedSynonym))
            return bothResults[1];

        return TNodeSetResult.empty();
    }

    private boolean withRefContainsSelectedSynonym(WithArgRefType leftHandType, WithArgumentRef leftHandRef, Synonym selectedSynonym) {
        Synonym firstSynonym;
        if (leftHandType.equals(WithArgRefType.PROG_LINE)) {
            firstSynonym = (Synonym) leftHandRef;
            return selectedSynonym.equalsToSynonym(firstSynonym);
        } else if (leftHandType.equals(ATTR_REF)) {
            firstSynonym = ((AttributeReference) leftHandRef).getSynonym();
            return selectedSynonym.equalsToSynonym(firstSynonym);
        }
        return false;
    }
}
