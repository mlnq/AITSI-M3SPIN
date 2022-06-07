package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.QueryProcessorException;
import aitsi.m3spin.query.model.enums.AttributeTypeEnum;

public class IncompatibleTypesComparisonException extends QueryProcessorException {
    public IncompatibleTypesComparisonException(AttributeTypeEnum rt1, AttributeTypeEnum rt2) {
        super(String.format("Could not compare references of incompatibile types: %s and %s",
                rt1, rt2));
    }
}
