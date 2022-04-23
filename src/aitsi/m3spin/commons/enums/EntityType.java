package aitsi.m3spin.commons.enums;

public enum EntityType{
    PROCEDURE("procedure"),
    EQUALS("="),
    CALL("call"),
    WHILE("while"),
    IF("if"),
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
//    ASSIGNMENT("assignment"),
    ASSIGNMENT("assign"),
    STATEMENT("stmt"),
    CONSTANT("constant"),
    VARIABLE("variable"),
    EXPRESSION("expression"),
    FACTOR("factor"),
    PROG_LINE("prog_line");

    private final String entityTypeName;

    EntityType(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getETName() {
        return entityTypeName;
    }
}
