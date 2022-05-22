package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.evaluator.clause.WithClauseEvaluator;
import aitsi.m3spin.query.model.Reference;
import aitsi.m3spin.query.model.Synonym;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    public Class<WithClauseEvaluator> getEvaluatorClass() {
        return WithClauseEvaluator.class;
    }

    public boolean usesSynonym(Reference reference, Synonym synonym) {
        return reference.equalsToSynonym(synonym);
    }
}
