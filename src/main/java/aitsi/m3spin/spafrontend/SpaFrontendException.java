package aitsi.m3spin.spafrontend;

public class SpaFrontendException extends Exception {
    public SpaFrontendException(String message) {
        super(String.format("SPA Front-End exception: %s", message));
    }
}
