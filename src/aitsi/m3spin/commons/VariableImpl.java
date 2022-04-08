package aitsi.m3spin.commons;

import aitsi.m3spin.commons.interfaces.TNODE;
import aitsi.m3spin.commons.interfaces.Variable;

public class VariableImpl implements Variable, TNODE {

    private String name;

    public VariableImpl(String name) {
        this.name = name;
    }
}
