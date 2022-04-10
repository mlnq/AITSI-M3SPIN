package aitsi.m3spin.pkb.exception;

public class SimplePkbException extends Exception{
    public SimplePkbException(String err) {
        super("Error while creating AST tree: " + err);
    }
}
