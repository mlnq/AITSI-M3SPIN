package aitsi.m3spin.commons.exception;

import aitsi.m3spin.spafrontend.parser.CodePosition;

public class IllegalCharacterException extends CodeScannerException {
    public IllegalCharacterException(char c, CodePosition cp, String expected) {
        super(String.format("Illegal character '%s' encountered at line %d, column %d. Expected %s.",
                c, cp.getLine(), cp.getColumn(), expected));
    }
}
