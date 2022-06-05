package aitsi.m3spin.query.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AttributeTypeEnum {
    STRING("STRING"),
    INTEGER("INTEGER");

    private final String value;
}
