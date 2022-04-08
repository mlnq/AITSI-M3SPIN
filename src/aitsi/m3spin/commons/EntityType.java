package aitsi.m3spin.commons;

public enum EntityType implements TNODE{
    PROGRAM,
    PROCEDURE("procedure"),
    STMTLIST,
    STMT,
    ASSIGN,
    CALL,
    WHILE,
    IF,
    PLUS,
    MINUS,
    TIMES,
    VARIABLE,
    CONSTANT;

    private String entityTypeName;

    EntityType(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getETName() {
        return entityTypeName;
    }
}
