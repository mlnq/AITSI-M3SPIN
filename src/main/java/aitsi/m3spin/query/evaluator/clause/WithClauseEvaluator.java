package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisionException;
import aitsi.m3spin.query.model.ReferenceType;
import aitsi.m3spin.query.model.clauses.WithClause;

import java.util.Set;

public class WithClauseEvaluator extends ClauseEvaluator {

    public WithClauseEvaluator(Pkb pkb, TNodeDao tNodeDao) {
        super(pkb, tNodeDao);
    }

    @Override
    public Set<TNode> evaluateClause(Set<TNode> previousResult) throws IncompatibleTypesComparisionException {
        WithClause withClause = (WithClause) pqlClause;
        ReferenceType leftHandType = withClause.getLeftHandReference().getType();
        ReferenceType rightHandType = withClause.getRightHandReference().getType();
        if (!leftHandType.equals(rightHandType))//todo ten if  do preprocessora
            throw new IncompatibleTypesComparisionException(leftHandType, rightHandType);

        Set<TNode> nodes = nodeDao.findAllByType(pkb.getAst().getRoot(), withClause.getSynonym().getType());//todo rozkminić, może singletonek?
//        najłatwiej to by było wstrzuknąć zależności Springiem

        nodes.stream()
                .filter(node -> node.getAttribute().equals(withClause.getValue()));
        return nodes;
    }
}
