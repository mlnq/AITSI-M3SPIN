package aitsi.m3spin.commons.exception;

import aitsi.m3spin.spafrontend.parser.CodePosition;

public class MissingKeywordException extends MissingCodeEntityException {
    public MissingKeywordException(String keyword, CodePosition cp) {
        super(keyword + " keyword", cp);
    }
}
