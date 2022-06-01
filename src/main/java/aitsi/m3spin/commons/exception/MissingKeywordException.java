package aitsi.m3spin.commons.exception;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.spafrontend.parser.CodePosition;

public class MissingKeywordException extends MissingCodeEntityException {
    public MissingKeywordException(EntityType keyword, CodePosition cp) {
        super(keyword + " keyword", cp);
    }
}
