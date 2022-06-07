package aitsi.m3spin.query.model.enums;

import aitsi.m3spin.commons.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttributeEnum {
    PROC_NAME(EntityType.PROCEDURE, "procName", AttributeTypeEnum.STRING),
    VAR_NAME(EntityType.VARIABLE, "varName", AttributeTypeEnum.STRING),
    VALUE(EntityType.CONSTANT, "value", AttributeTypeEnum.INTEGER),
    STMT_NUMBER(EntityType.STATEMENT, "stmt#", AttributeTypeEnum.INTEGER),
    CALL_PROC_NAME(EntityType.CALL, "procName", AttributeTypeEnum.STRING);

    private final EntityType entityType;
    private final String attrName;
    private final AttributeTypeEnum attrType;

    public static boolean contains(String name) {

        for (AttributeEnum an : AttributeEnum.values()) {
            if (an.getAttrName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
