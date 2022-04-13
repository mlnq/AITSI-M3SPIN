package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class TNodeImpl implements TNode {
    TNode leftSibling;
    TNode rightSibling;
    TNode parent;
    TNode firstChild;
    TNode secondChild;
    TNode thirdChild;
    String attribute;
    int stmtLine;

    public abstract EntityType getType();
}
