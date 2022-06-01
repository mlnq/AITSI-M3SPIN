package aitsi.m3spin.pkb.model;

import aitsi.m3spin.commons.interfaces.NodeAttribute;
import aitsi.m3spin.commons.interfaces.TNode;

public interface AttributableNode extends TNode {
    NodeAttribute getAttribute();

    void setAttribute(NodeAttribute attribute);
}
