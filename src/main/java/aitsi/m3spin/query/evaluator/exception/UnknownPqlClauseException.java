package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.model.clauses.PqlClause;

public class UnknownPqlClauseException extends QueryEvaluatorException {
    public UnknownPqlClauseException(PqlClause clause) {
        super(String.format("Encountered clause %s of unrecognized type.", clause));
    }
}
