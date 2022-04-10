package aitsi.m3spin.parser.exception;

import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.parser.CodePosition;

public class MissingSimpleKeywordException extends MissingCodeEntityException {
    public MissingSimpleKeywordException(EntityType keyword, CodePosition cp) {
        super(keyword + " keyword", cp);
    }
}
