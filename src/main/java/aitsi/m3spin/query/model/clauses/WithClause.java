package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.evaluator.clause.WithClauseEvaluator;
import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.references.WithArgumentRef;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithClause implements PqlClause {
    private WithArgumentRef leftHandReference;
    private WithArgumentRef rightHandReference;

    public WithArgumentRef[] getBothReferences() {
        return new WithArgumentRef[]{leftHandReference, rightHandReference};
    }

    @Override
    public boolean usesSynonym(Synonym synonym) throws IncompatibleTypesComparisonException {
        return usesSynonym(leftHandReference, synonym) || usesSynonym(rightHandReference, synonym);
    }

    @Override
    public Class<WithClauseEvaluator> getEvaluatorClass() {
        return WithClauseEvaluator.class;
    }

    public boolean usesSynonym(WithArgumentRef reference, Synonym synonym) throws IncompatibleTypesComparisonException {
        return reference.equalsToSynonym(synonym);
    }
}
