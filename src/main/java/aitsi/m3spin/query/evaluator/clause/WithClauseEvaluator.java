package aitsi.m3spin.query.evaluator.clause;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.dao.TNodeDao;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisionException;
import aitsi.m3spin.query.model.clauses.PqlClause;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
public class WithClauseEvaluator extends ClauseEvaluator {

    public WithClauseEvaluator(Pkb pkb, TNodeDao tNodeDao, PqlClause pqlClause) {
        super(pkb, tNodeDao, pqlClause);
    }

    @Override
    public Set<TNode> evaluateClause(Set<TNode> previousResult) throws IncompatibleTypesComparisionException {
//        WithClause withClause = (WithClause) pqlClause;
//        ReferenceType leftHandType = withClause.getLeftHandReference().getSynonymType();
//        ReferenceType rightHandType = withClause.getRightHandReference().getSynonymType();
//        if (!leftHandType.equals(rightHandType))//todo ten if  do preprocessora
//            throw new IncompatibleTypesComparisionException(leftHandType, rightHandType);
//
//        Set<TNode> nodes = nodeDao.findAllByType(pkb.getAst().getRoot(), withClause.getSynonym().getType());//todo rozkminić, może singletonek?
////        najłatwiej to by było wstrzuknąć zależności Springiem
//
//        nodes.stream()
//                .filter(node -> node.getAttribute().equals(withClause.getValue()));
//        return nodes;
        return Collections.emptySet();
    }
}
