package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.query.model.AttributeEnum;
import aitsi.m3spin.query.model.Synonym;

public class NoSuchAttributeException extends QueryEvaluatorException {
    public NoSuchAttributeException(Synonym synonym, AttributeEnum attribute) {
        super(String.format("Synonym %s of type %s does not have an attribute '%s'",
                synonym.getName(), synonym.getType(), attribute.getAttrName()));
    }
}
