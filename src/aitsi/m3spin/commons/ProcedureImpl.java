package aitsi.m3spin.commons;

import aitsi.m3spin.commons.interfaces.Procedure;

public class ProcedureImpl implements Procedure {
    private String name;

    public ProcedureImpl(String name) {
        this.name = name;
    }
}
