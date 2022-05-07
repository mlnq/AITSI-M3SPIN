package aitsi.m3spin.commons.enums;

public enum EntityType {
    //    PROGRAM("PROGRAM"),
    PROCEDURE("procedure"),
    STMT_LIST("statementList"),
    //    STMT("STMT"),
    EQUALS("="),
    CALL("call"),
    WHILE("while"),
    IF("if"),
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    ASSIGNMENT("assignment"),
    CONSTANT("constant"),
    VARIABLE("variable"),
    EXPRESSION("expression"),
    FACTOR("factor");

    private final String entityTypeName;

    EntityType(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getETName() {
        return entityTypeName;
    }
}
