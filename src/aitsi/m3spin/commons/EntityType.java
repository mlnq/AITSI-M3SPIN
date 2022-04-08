package aitsi.m3spin.commons;

import aitsi.m3spin.commons.interfaces.TNODE;

public enum EntityType implements TNODE {
    PROGRAM("PROGRAM"),
    PROCEDURE("procedure"),
    STMT_LIST("STMTLIST"),
    STMT("STMT"),
    ASSIGN("ASSIGN"),
    CALL("CALL"),
    WHILE("WHILE"),
    IF("IF"),
    PLUS("PLUS"),
    MINUS("MINUS"),
    TIMES("TIMES"),
    VARIABLE("VARIABLE"),
    CONSTANT("CONSTANT");

    private String entityTypeName;

    EntityType(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getETName() {
        return entityTypeName;
    }
}
