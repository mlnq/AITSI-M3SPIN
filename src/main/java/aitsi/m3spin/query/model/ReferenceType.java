package aitsi.m3spin.query.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReferenceType {
    STRING("STRING"),
    INTEGER("INTEGER"),
    SYNONYM("SYNONYM");

    private final String value;

    public static ReferenceType fromAttrType(AttributeTypeEnum attributeTypeEnum) {
        return ReferenceType.valueOf(attributeTypeEnum.getValue());
    }
}
