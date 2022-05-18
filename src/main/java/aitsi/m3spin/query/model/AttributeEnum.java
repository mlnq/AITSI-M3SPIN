package aitsi.m3spin.query.model;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.interfaces.TNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttributeEnum {
    PROC_NAME(EntityType.PROCEDURE, "procName"),
    VAR_NAME(EntityType.VARIABLE, "varName"),
    VALUE(EntityType.CONSTANT, "value"),
    STMT_NUMBER(EntityType.STATEMENT,"stmt#"),
    CALL_PROC_NAME(EntityType.CALL,"procName");

    private final EntityType entityType;
    private final String attrName;
}