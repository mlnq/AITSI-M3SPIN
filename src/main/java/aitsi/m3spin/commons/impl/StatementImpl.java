package aitsi.m3spin.commons.impl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class StatementImpl extends TNodeImpl {
    protected int stmtLine;
}
