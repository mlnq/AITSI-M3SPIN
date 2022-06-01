package aitsi.m3spin.query.model.references;

import aitsi.m3spin.query.model.enums.AttributeTypeEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReferenceType {
    STRING("STRING"),
    INTEGER("INTEGER"),
    SYNONYM("SYNONYM"),

    WILDCARD("WILDCARD");

    private final String value;

    public static ReferenceType fromAttrType(AttributeTypeEnum attributeTypeEnum) {
        return ReferenceType.valueOf(attributeTypeEnum.getValue());
    }
}
