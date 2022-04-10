package aitsi.m3spin.parser.exception;

public class SimpleParserException extends Exception {
    public SimpleParserException(String err) {
        super("Error while parsing SIMPLE code: " + err);
    }
}
