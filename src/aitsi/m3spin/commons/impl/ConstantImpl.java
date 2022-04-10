package aitsi.m3spin.commons.impl;


import aitsi.m3spin.commons.interfaces.Constant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConstantImpl extends TNodeImpl implements Constant {
    private int value;
}
