package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
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

@EqualsAndHashCode(callSuper = false)
public class WithClauseEvaluator extends ClauseEvaluator {

    public WithClauseEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }

    @Override
    public QueryResult evaluateClause(TNodeSetResult previousResult, SelectedResult selectedResult) throws IncompatibleTypesComparisonException {
        WithClause withClause = (WithClause) pqlClause;

        ReferenceType leftHandType = withClause.getLeftHandReference().getReferenceType();
        ReferenceType rightHandType = withClause.getRightHandReference().getReferenceType();
        if (!leftHandType.equals(rightHandType))//todo ten if  do preprocessora
            throw new IncompatibleTypesComparisonException(leftHandType, rightHandType);

        Reference leftHandRef = withClause.getLeftHandReference();
        Reference rightHandRef = withClause.getRightHandReference();

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

            Set<TNode> tNodes = tNodeDao.findAllByType(pkb.getAst().getRoot(), attributeRef[0].getSynonym().getSynonymType());

            return new TNodeSetResult(tNodes);

        } else {
            Set<TNode> leftResult = tNodeDao.findAllByType(pkb.getAst().getRoot(),
                    ((ComplexTypeReference) leftHandRef).getSynonym().getSynonymType());

            Set<TNode> rightResult = tNodeDao.findAllByType(pkb.getAst().getRoot(),
                    ((ComplexTypeReference) rightHandRef).getSynonym().getSynonymType());

            leftResult.retainAll(rightResult);

            return new TNodeSetResult(leftResult);
        }
    }
}
