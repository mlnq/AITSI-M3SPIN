package aitsi.m3spin.pkb.exception;

import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.parser.exception.IllegalCharacterException;

public class IllegalLinkTypeException extends SimplePkbException{
    public IllegalLinkTypeException(LinkType type){
        super("Illegal link type: '" + type + "'");
    }

    public IllegalLinkTypeException(LinkType type, TNode node1, TNode node2){
        super("Illegal link type: '" + type + "' When trying to link nodes: node1: '" + node1.getAttribute() + "' and '" + node2.getAttribute());
    }

    public IllegalLinkTypeException(String err) {
        super(err);
    }
}
