package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public abstract class TNodeImpl implements TNode {
    protected TNode leftSibling;
    protected TNode rightSibling;
    protected TNode parent;
    protected TNode child;
    protected String attribute;

    public abstract EntityType getType();
}
