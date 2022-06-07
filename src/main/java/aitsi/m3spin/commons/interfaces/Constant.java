package aitsi.m3spin.commons.interfaces;

import aitsi.m3spin.pkb.model.AttributableNode;

public interface Constant extends Factor, AttributableNode {
    int getValue();

    void setValue(int value);
}
