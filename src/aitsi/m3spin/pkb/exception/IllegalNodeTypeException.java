package aitsi.m3spin.pkb.exception;

import aitsi.m3spin.commons.enums.EntityType;

public class IllegalNodeTypeException extends SimplePkbException {
    public IllegalNodeTypeException(EntityType nodeType) {
        super("Illegal node type: '" + nodeType + "'");
    }
}
