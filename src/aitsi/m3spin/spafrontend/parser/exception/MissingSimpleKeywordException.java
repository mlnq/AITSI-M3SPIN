package aitsi.m3spin.spafrontend.parser.exception;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.spafrontend.parser.CodePosition;

public class MissingSimpleKeywordException extends MissingCodeEntityException {
    public MissingSimpleKeywordException(EntityType keyword, CodePosition cp) {
        super(keyword + " keyword", cp);
    }
}
