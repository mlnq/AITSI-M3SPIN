package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.references.ReferenceType;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
public class WithClauseEvaluator extends ClauseEvaluator {

    public WithClauseEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }

    @Override
    public Set<TNode> evaluateClause(Set<TNode> previousResult) throws IncompatibleTypesComparisonException {
        WithClause withClause = (WithClause) pqlClause;

        ReferenceType leftHandType = withClause.getLeftHandReference().getReferenceType();
        ReferenceType rightHandType = withClause.getRightHandReference().getReferenceType();
        if (!leftHandType.equals(rightHandType))//todo ten if  do preprocessora
            throw new IncompatibleTypesComparisonException(leftHandType, rightHandType);

//        Arrays.stream(withClause.getBothReferences())
//                .filter(reference -> !reference.isConstantValue())
//                .forEach(reference ->);//todo tutaj jestem ~Paweł
//
//        Set<TNode> nodes = super.tNodeDao.findAllByType(pkb.getAst().getRoot(), withClause.get().getType());
//
//        nodes.stream()
//                .filter(node -> node.getAttribute().equals(withClause.getValue()));
//        return nodes;
        return Collections.emptySet();
    }
}
