package aitsi.m3spin.query.preprocessor.exceptions;

public class NameNotDeclaredException extends QueryPreprocessorException {
    public NameNotDeclaredException(String name) {
        super("Name " + name + " not declared");
    }
}
