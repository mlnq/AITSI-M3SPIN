package aitsi.m3spin.commons.impl;

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
    @EqualsAndHashCode.Exclude
    protected TNode leftSibling;
    @EqualsAndHashCode.Exclude
    protected TNode rightSibling;
    @EqualsAndHashCode.Exclude
    protected TNode parent;
    @EqualsAndHashCode.Exclude
    protected TNode child;
    protected String attribute;
}
