package aitsi.m3spin.query.preprocessor.exceptions;

public class SynonymNotDeclared extends QueryPreprocessorException {
    public SynonymNotDeclared(String name) {
        super(String.format("Synonym '%s' is not declared.", name));
    }
}
