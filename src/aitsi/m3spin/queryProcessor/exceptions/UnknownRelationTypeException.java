package aitsi.m3spin.queryProcessor.exceptions;

public class UnknownRelationTypeException extends Throwable {
    public UnknownRelationTypeException(String relationName) {
        super("Error while parsing PQL query: Relation name " + relationName + " not declared");
    }
}
