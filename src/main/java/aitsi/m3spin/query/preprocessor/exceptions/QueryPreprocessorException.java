package aitsi.m3spin.query.preprocessor.exceptions;

import aitsi.m3spin.query.QueryProcessorException;

public class QueryPreprocessorException extends QueryProcessorException {
    public QueryPreprocessorException(String message) {
        super("Error while parsing PQL query: " + message);
    }
}
