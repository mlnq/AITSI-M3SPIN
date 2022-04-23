package aitsi.m3spin.queryProcessor.exceptions;

public class NameNotDeclaredException extends Throwable {
    public NameNotDeclaredException(String name) {
        super("Error while parsing PQL query: Name " + name + " not declared");
    }
}
