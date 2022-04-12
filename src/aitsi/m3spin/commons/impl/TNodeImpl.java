package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.TNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TNodeImpl implements TNode {
    TNode leftSibling;
    TNode rightSibling;
    TNode parent;
    TNode firstChild;
    TNode secondChild;
    TNode thirdChild;
    String attribute;
    int stmtLine;
}
