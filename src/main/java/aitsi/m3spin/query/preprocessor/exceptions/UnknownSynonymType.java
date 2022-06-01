package aitsi.m3spin.query.preprocessor.exceptions;

public class UnknownSynonymType extends QueryPreprocessorException {
    public UnknownSynonymType(String firstWord) {
        super("Encountered unknown synonym type: " + firstWord);

    }
}
