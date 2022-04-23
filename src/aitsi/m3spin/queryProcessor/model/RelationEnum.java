package aitsi.m3spin.queryProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RelationEnum {
    PARENT("Parent"),
    PARENT_T("Parent*"),
    FOLLOWS("Follows"),
    FOLLOWS_T("Follows*"),
    MODIFIES("Modifies"),
    USES("Uses");

    private final String relationName;
}
