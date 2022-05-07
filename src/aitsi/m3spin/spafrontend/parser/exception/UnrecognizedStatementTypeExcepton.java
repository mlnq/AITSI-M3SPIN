package aitsi.m3spin.spafrontend.parser.exception;

public class UnrecognizedStatementTypeExcepton extends SimpleParserException {
    public UnrecognizedStatementTypeExcepton() {
        super("Statement type not recognized.");
    }

    public UnrecognizedStatementTypeExcepton(String err) {
        super(err);
    }
}
