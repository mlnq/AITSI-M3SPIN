package aitsi.m3spin.commons.enums;

import aitsi.m3spin.commons.interfaces.TNode;

public enum EntityType{
    //    PROGRAM("PROGRAM"),
    PROCEDURE("procedure"),
    //    STMT_LIST("STMTLIST"),
//    STMT("STMT"),
    EQUALS("="),
    CALL("call"),
    WHILE("while"),
    IF("if"),
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    ASSIGNMENT("assign"),
    CONSTANT("contant"),
    VARIABLE("variable"),
    EXPRESSION("expression"),
    FACTOR("factor");
//    CONSTANT("CONSTANT");

    private String entityTypeName;

    EntityType(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getETName() {
        return entityTypeName;
    }
}
