package aitsi.m3spin.parser.exception;

import aitsi.m3spin.parser.CodePosition;

public class IllegalCharacterException extends SimpleParserException {
    public IllegalCharacterException(char c, CodePosition cp, String placeOfOccurence) {
        super("Illegal character '" + c + "' encountered in " + placeOfOccurence + " at line " + cp.getLine() + ", column " + cp.getColumn());
    }

    public IllegalCharacterException(char c, CodePosition cp) {
        super("Illegal character '" + c + "' encountered at line " + cp.getLine() + ", column " + cp.getColumn());
    }
}
