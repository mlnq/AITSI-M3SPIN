package aitsi.m3spin.pkb.exception;

public class AstCreationException extends PkbException {
    public AstCreationException(String err) {
        super("Error while creating AST tree: " + err);
    }
}
