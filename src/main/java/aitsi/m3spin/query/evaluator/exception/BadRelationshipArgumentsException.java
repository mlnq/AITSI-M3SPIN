package aitsi.m3spin.query.evaluator.exception;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.query.model.enums.RelationshipEnum;

public class BadRelationshipArgumentsException extends ClauseEvaluationException {
    public BadRelationshipArgumentsException(RelationshipEnum relationship, EntityType firstType, EntityType secondType) {
        super(String.format(
                "Bad relationship argument(s) types for relationship %s. Expected (%s, %s), but received (%s, %s)",
                relationship.getRelationName(),
                relationship.getAllowedFirstArgTypes(),
                relationship.getAllowedSecondArgTypes(),
                firstType, secondType));
    }
}
