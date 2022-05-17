package aitsi.m3spin.spafrontend.parser.exception;

import aitsi.m3spin.spafrontend.parser.CodePosition;

public class MissingCharacterException extends MissingCodeEntityException {
    public MissingCharacterException(char c, CodePosition cp) {
        super("'"+ c +"' character", cp);
    }

    public MissingCharacterException(CodePosition cp) {
        super("character", cp);
    }
}
