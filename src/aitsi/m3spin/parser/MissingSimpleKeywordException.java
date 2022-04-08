package aitsi.m3spin.parser;

import aitsi.m3spin.commons.EntityType;

public class MissingSimpleKeywordException extends Exception {
    public MissingSimpleKeywordException(EntityType keyword, CodePosition cp) {
        super("Missing " + keyword + " keyword at line " + cp.getLine() + ", column " + cp.getColumn());
    }
}
