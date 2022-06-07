package aitsi.m3spin.commons.enums;

public enum EntityType {
    PROCEDURE("procedure"),
    STMT_LIST("stmtLst"),
    EQUALS("="),
    CALL("call"),
    WHILE("while"),
    IF("if"),
    THEN("then"),
    ELSE("else"),
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

    public static boolean contains(String name) {
        for (EntityType et : EntityType.values()) {
            if (et.getETName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static EntityType fromString(String etName) {
        for (EntityType et : EntityType.values()) {
            if (et.entityTypeName.equalsIgnoreCase(etName)) {
                return et;
            }
        }
        return null;
    }

    public String getETName() {
        return entityTypeName;
    }
}
