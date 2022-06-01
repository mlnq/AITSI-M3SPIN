package aitsi.m3spin.query.preprocessor.exceptions;

import aitsi.m3spin.query.model.enums.RelationshipPreprocEnum;

public class BadRelationshipArgumentTypeException extends QueryPreprocessorException {
    public BadRelationshipArgumentTypeException(RelationshipPreprocEnum relationEnum, String string) {
        super("Relation " + relationEnum + " do not have argument type: " + string);
    }
}
