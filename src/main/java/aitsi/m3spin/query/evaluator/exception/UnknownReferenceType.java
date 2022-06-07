package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.references.ReferenceType;

public class UnknownReferenceType extends ClauseEvaluationException {
    public UnknownReferenceType(PqlClause pqlClause, ReferenceType referenceType) {
        super(pqlClause, String.format("unknown argument reference type: %s", referenceType));
    }
}
