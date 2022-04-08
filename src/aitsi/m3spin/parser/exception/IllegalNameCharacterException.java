package aitsi.m3spin.parser.exception;

import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.parser.CodePosition;

public class IllegalNameCharacterException extends IllegalCharacterException {
    public IllegalNameCharacterException(char c, CodePosition cp) {
        super(c, cp, "entity name");
    }
}
