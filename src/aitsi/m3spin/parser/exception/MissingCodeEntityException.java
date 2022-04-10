package aitsi.m3spin.parser.exception;

import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.parser.CodePosition;

public class MissingCodeEntityException extends SimpleParserException{
    public MissingCodeEntityException(String codeEntity, CodePosition cp) {
        super("Missing " + codeEntity + " at line " + cp.getLine() + ", column " + cp.getColumn());
    }
}
