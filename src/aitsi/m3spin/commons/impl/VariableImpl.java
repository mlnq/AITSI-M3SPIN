package aitsi.m3spin.commons.impl;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.commons.interfaces.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VariableImpl implements Variable, TNode {
    private String name;
}
