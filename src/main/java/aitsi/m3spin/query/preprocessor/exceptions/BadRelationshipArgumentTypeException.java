package aitsi.m3spin.query.preprocessor.exceptions;

import aitsi.m3spin.query.model.enums.RelationshipPreprocEnum;
import aitsi.m3spin.query.model.references.ReferenceType;

public class BadRelationshipArgumentTypeException extends QueryPreprocessorException {
    private BadRelationshipArgumentTypeException(RelationshipPreprocEnum relationEnum, ReferenceType receivedType, String argNumber) {
        super(String.format("Relationship %s does not accept %s as %s argument", relationEnum, receivedType, argNumber));
    }

    private BadRelationshipArgumentTypeException(RelationshipPreprocEnum relationEnum, String argNumber) {
        super(String.format("Relationship %s received unknown reference type as %s argument", relationEnum, argNumber));
    }

    public static BadRelationshipArgumentTypeException ofNotAllowedRefType(RelationshipPreprocEnum relationEnum,
                                                                           ReferenceType receivedType, boolean isFirstArg) {
        String argNumber = isFirstArg ? "first" : "second";
        return new BadRelationshipArgumentTypeException(relationEnum, receivedType, argNumber);
    }

    public static BadRelationshipArgumentTypeException ofUnknownRefType(RelationshipPreprocEnum relationEnum, boolean isFirstArg) {
        String argNumber = isFirstArg ? "first" : "second";
        return new BadRelationshipArgumentTypeException(relationEnum, argNumber);
    }
}
