package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisionException;
import aitsi.m3spin.query.model.Reference;
import aitsi.m3spin.query.model.Synonym;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class WithClause implements PqlClause {
    private Reference leftHandReference;
    private Reference rightHandReference;

    @Override
    public boolean usesSynonym(Synonym synonym) {
        return usesSynonym(leftHandReference, synonym) || usesSynonym(rightHandReference, synonym);
    }

    @Override
    public boolean evaluate() throws IncompatibleTypesComparisionException {
        if (!leftHandReference.getType().equals(rightHandReference.getType()))//todo ten if  do preprocessora
            throw new IncompatibleTypesComparisionException(leftHandReference.getType(), rightHandReference.getType());

        Set<TNode> nodes = nodeDao.findAllByType(pkb.getAst().getRoot(), withClause.getSynonym().getType());//todo rozkminić, może singletonek?
//        najłatwiej to by było wstrzuknąć zależności Springiem

        nodes.stream()
                .filter(node -> node.getAttribute().equals(withClause.getValue()));
        return nodes;
    }

    @Override
    public Set<TNode> evaluate(Set<TNode> previousResult) {
        return null;
    }

    public boolean usesSynonym(Reference reference, Synonym synonym) {
        return reference.equalsToSynonym(synonym);
    }
}
