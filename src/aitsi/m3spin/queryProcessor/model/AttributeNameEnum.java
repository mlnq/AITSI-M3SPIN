package aitsi.m3spin.queryProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttributeNameEnum {
    VAR_NAME("varName"),
    STMT_NUMBER("stmt#");

    private final String attrName;
}
