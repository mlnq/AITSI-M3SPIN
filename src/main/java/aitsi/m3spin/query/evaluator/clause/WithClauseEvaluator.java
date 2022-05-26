package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.references.ComplexTypeReference;
import aitsi.m3spin.query.model.references.PrimitiveTypeReference;
import aitsi.m3spin.query.model.references.Reference;
import aitsi.m3spin.query.model.references.ReferenceType;
import aitsi.m3spin.query.model.result.BooleanResult;
import aitsi.m3spin.query.model.result.QueryResult;
import aitsi.m3spin.query.model.result.SelectedResult;
import aitsi.m3spin.query.model.result.TNodeSetResult;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashSet;
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

        Set<TNode> leftResult = new HashSet<>();//todo
        Set<TNode> rightResult = new HashSet<>();

        if (!leftHandRef.isConstantValue()) {
            ComplexTypeReference complexReference = (ComplexTypeReference) leftHandRef;
            leftResult = tNodeDao.findAllByType(pkb.getAst().getRoot(), complexReference.getSynonym().getSynonymType());
        }
        if (!rightHandRef.isConstantValue()) {
            ComplexTypeReference complexReference = (ComplexTypeReference) leftHandRef;
            rightResult = tNodeDao.findAllByType(pkb.getAst().getRoot(), complexReference.getSynonym().getSynonymType());
        }

        if (leftHandRef.isConstantValue() && rightHandRef.isConstantValue()) {
            if (((PrimitiveTypeReference) leftHandRef).getValue()
                    .equals(((PrimitiveTypeReference) rightHandRef).getValue()))
                return new BooleanResult(true);
            else return new BooleanResult(false);
        } else if (leftHandRef.isConstantValue() ^ rightHandRef.isConstantValue()) {
//todo wip Paweł
        } else {

        }
        return new TNodeSetResult(Collections.emptySet());
    }
}
