package aitsi.m3spin.spafrontend.parser.exception;

import aitsi.m3spin.spafrontend.parser.CodePosition;

public class UnknownStatementType extends SimpleParserException {
    public UnknownStatementType(String err) {
        super(err);
    }

    public UnknownStatementType(CodePosition codePosition) {
        super("Encountered unknown statement type at line " +
                codePosition.getLine() + ", column " + codePosition.getColumn());
    }
}
