package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;

public class VariableImpl implements Variable, TNode {

    private String name;

    public VariableImpl(String name) {
        this.name = name;
    }
}
