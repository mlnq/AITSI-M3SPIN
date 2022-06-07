package aitsi.m3spin.query.preprocessor;

import aitsi.m3spin.query.preprocessor.exceptions.QueryPreprocessorException;

public class UnknownWithReferenceException extends QueryPreprocessorException {
    public UnknownWithReferenceException(char unexpectedChar) {
        super("Could not parse one of With clause references. Unexpected character: '" + unexpectedChar + "'");
    }
}
