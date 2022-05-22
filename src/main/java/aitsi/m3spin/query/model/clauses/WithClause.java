package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.evaluator.clause.WithClauseEvaluator;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.model.references.Reference;
import aitsi.m3spin.query.model.references.Synonym;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithClause implements PqlClause {
    private Reference leftHandReference;
    private Reference rightHandReference;

    public Reference[] getBothReferences() {
        return new Reference[]{leftHandReference, rightHandReference};
    }

    @Override
    public boolean usesSynonym(Synonym synonym) throws IncompatibleTypesComparisonException {
        return usesSynonym(leftHandReference, synonym) || usesSynonym(rightHandReference, synonym);
    }

    @Override
    public Class<WithClauseEvaluator> getEvaluatorClass() {
        return WithClauseEvaluator.class;
    }

    public boolean usesSynonym(Reference reference, Synonym synonym) throws IncompatibleTypesComparisonException {
        return reference.equalsToSynonym(synonym);
    }
}
