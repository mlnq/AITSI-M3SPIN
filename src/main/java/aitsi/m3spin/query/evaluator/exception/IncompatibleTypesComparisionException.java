package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.model.ReferenceType;

public class IncompatibleTypesComparisionException extends QueryEvaluatorException {
    public IncompatibleTypesComparisionException(ReferenceType rt1, ReferenceType rt2) {
        super(String.format("Could not compare references of incompatibile types: %s and %s",
                rt1, rt2));
    }
}
