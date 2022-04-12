package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VariableImpl extends TNodeImpl implements Variable {
    private int ID;

    public VariableImpl(String name) {
        super.attribute = name;
    }
}
