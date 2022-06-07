package aitsi.m3spin.query.preprocessor.exceptions;

import aitsi.m3spin.query.model.enums.AttributeEnum;
import aitsi.m3spin.query.model.references.Synonym;

public class BadAttributeException extends QueryPreprocessorException {
    public BadAttributeException(String message) {
        super(message);
    }

    public BadAttributeException(AttributeEnum attributeEnum, Synonym synonym) {
        super(String.format("Bad attribute reference: '%s.%s'. This attribute exists only in %s entity",
                synonym.getName(), attributeEnum.getAttrName(), attributeEnum.getEntityType()));
    }
}
