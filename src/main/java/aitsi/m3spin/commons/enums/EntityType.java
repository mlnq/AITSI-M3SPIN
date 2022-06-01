package aitsi.m3spin.commons.enums;

public enum EntityType {
    PROCEDURE("procedure"),
    STMT_LIST("stmtLst"),
    EQUALS("="),
    CALL("call"),
    WHILE("while"),
    IF("if"),
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
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

    public static boolean contains(String test) {
        for (EntityType et : EntityType.values()) {
            if (et.getETName().equals(test)) {
                return true;
            }
        }
        return false;
    }

    public String getETName() {
        return entityTypeName;
    }
}
