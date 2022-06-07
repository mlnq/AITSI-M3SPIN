package aitsi.m3spin.commons.exception;

import aitsi.m3spin.spafrontend.parser.CodePosition;

public class MissingCodeEntityException extends CodeScannerException {
    public MissingCodeEntityException(String codeEntity, CodePosition cp) {
        super("Missing " + codeEntity + " at line " + cp.getLine() + ", column " + cp.getColumn());
    }
}
