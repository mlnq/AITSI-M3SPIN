package aitsi.m3spin.parser.exception;

import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.parser.CodePosition;

public class SimpleParserException extends Exception {
    public SimpleParserException(String err) {
        super("Error while parsing SIMPLE code: " + err);
    }
}
