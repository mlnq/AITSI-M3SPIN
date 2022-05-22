package aitsi.m3spin.query.model;

import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SimpleEntityName implements RelationshipArgumentRef {
    private String entityName;
}
