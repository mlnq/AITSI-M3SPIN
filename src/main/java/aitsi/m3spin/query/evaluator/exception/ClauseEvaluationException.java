package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.model.clauses.PqlClause;

public class ClauseEvaluationException extends QueryEvaluatorException {
    public ClauseEvaluationException(PqlClause pqlClause) {
        super("Error while evaluating PQL clause: " + pqlClause);
    }

    public ClauseEvaluationException(PqlClause pqlClause, String msg) {
        super(String.format("Error while evaluating PQL clause: %s. Reason: %s", pqlClause, msg));
    }
}
