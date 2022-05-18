package aitsi.m3spin.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SimpleEntityName implements RelationArgument {
    private String entityName;
}