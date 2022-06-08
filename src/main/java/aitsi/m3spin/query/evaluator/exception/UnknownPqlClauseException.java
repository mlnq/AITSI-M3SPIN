package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.spafrontend.parser.CodePosition;

public class UnknownPqlClauseException extends QueryEvaluatorException {
    public UnknownPqlClauseException(PqlClause clause) {
        super(String.format("Encountered clause %s of unrecognized type.", clause));
    }

    public UnknownPqlClauseException(String unknownClause, CodePosition cp) {
        super(String.format("Encountered unknown clause '%s' at %s.", unknownClause, cp));
    }
}
