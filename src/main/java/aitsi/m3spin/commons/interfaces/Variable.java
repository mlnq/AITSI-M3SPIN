package aitsi.m3spin.commons.interfaces;

import aitsi.m3spin.pkb.model.AttributableNode;

public interface Variable extends Factor, AttributableNode {
    int getId();

    String getVarName();

    void setVarName(String varName);
}
