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

    public boolean usesSynonym(Reference reference, Synonym synonym) {
        return reference.equalsToSynonym(synonym);
    }
}
