package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.model.clauses.PqlClause;

public class UnknownRelationType extends ClauseEvaluationException {
    public UnknownRelationType(PqlClause pqlClause) {
        super(pqlClause, "encountered unknown relation type during Such that evaluation.");
    }
}
