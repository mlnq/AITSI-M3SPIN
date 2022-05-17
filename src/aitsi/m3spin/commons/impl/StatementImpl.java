package aitsi.m3spin.commons.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class StatementImpl extends TNodeImpl {
    protected int stmtLine;
}
