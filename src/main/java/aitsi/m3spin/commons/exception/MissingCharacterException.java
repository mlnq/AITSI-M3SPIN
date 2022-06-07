package aitsi.m3spin.commons.exception;

import aitsi.m3spin.spafrontend.parser.CodePosition;

public class MissingCharacterException extends MissingCodeEntityException {
    public MissingCharacterException(CodePosition cp) {
        super("character", cp);
    }

    public MissingCharacterException(String charType, CodePosition cp) {
        super(String.format("%s character", charType), cp);
    }

    public MissingCharacterException(char c, CodePosition cp) {
        super(String.format("'%s' character", c), cp);
    }
}
