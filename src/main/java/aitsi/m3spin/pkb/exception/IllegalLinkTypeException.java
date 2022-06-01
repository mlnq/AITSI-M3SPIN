package aitsi.m3spin.pkb.exception;

import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.TNode;

public class IllegalLinkTypeException extends AstCreationException {
    public IllegalLinkTypeException(LinkType type) {
        super("Illegal link type: '" + type + "'");
    }

    public IllegalLinkTypeException(LinkType type, TNode node1, TNode node2) {
        super("Illegal link type: '" + type +
                "' when trying to link nodes: '" + node1.toString() + "' and '" + node2.toString());
    }

    public IllegalLinkTypeException(String err) {
        super(err);
    }
}
