package aitsi.m3spin.spafrontend.parser.exception;

import aitsi.m3spin.spafrontend.parser.CodePosition;

public class IllegalNameCharacterException extends IllegalCharacterException {
    public IllegalNameCharacterException(char c, CodePosition cp) {
        super(c, cp, "entity name");
    }
}
