package aitsi.m3spin.query.model.clauses;

import aitsi.m3spin.query.evaluator.clause.WithClauseEvaluator;
import aitsi.m3spin.query.model.enums.WithArgRefType;
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
    public boolean usesSynonym(Synonym synonym) {
        return usesSynonym(leftHandReference, synonym) || usesSynonym(rightHandReference, synonym);
    }

    @Override
    public Class<WithClauseEvaluator> getEvaluatorClass() {
        return WithClauseEvaluator.class;
    }

    @Override
    public Synonym getOtherUsedSynonym(Synonym excludedSynonym) {
        if (leftHandReference.equals(excludedSynonym) && rightHandReference.getWithArgRefType().equals(WithArgRefType.PROG_LINE))
            return (Synonym) rightHandReference;
        else if (rightHandReference.equals(excludedSynonym) && leftHandReference.getWithArgRefType().equals(WithArgRefType.PROG_LINE))
            return (Synonym) leftHandReference;
        else return null;
    }

    public boolean usesSynonym(WithArgumentRef reference, Synonym synonym) {
        return reference.equalsToSynonym(synonym);
    }


}
