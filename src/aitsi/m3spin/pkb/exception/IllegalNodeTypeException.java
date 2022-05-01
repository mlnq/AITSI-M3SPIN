package aitsi.m3spin.pkb.exception;

import aitsi.m3spin.commons.enums.EntityType;

public class IllegalNodeTypeException extends AstCreationException {
    public IllegalNodeTypeException(EntityType nodeType) {
        super("Illegal node type: '" + nodeType + "'");
    }
}
