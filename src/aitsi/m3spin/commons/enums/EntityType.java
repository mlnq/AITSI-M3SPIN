package aitsi.m3spin.commons.enums;

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
//    ASSIGNMENT("assignment"),
    ASSIGNMENT("assign"),

    STATEMENT("stmt"),
    CONSTANT("constant"),
    VARIABLE("variable"),
    EXPRESSION("expression"),
    FACTOR("factor");

    private String entityTypeName;

    EntityType(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getETName() {
        return entityTypeName;
    }
}
