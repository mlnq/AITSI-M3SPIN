package aitsi.m3spin.query.preprocessor.exceptions;

public class UnknownRelationTypeException extends QueryPreprocessorException {
    public UnknownRelationTypeException(String relationName) {
        super(String.format("Unknown relation name: '%s'.", relationName));
    }
}
