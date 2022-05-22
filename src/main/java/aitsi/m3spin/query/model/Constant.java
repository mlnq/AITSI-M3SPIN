package aitsi.m3spin.query.model;

import aitsi.m3spin.query.model.relationships.RelationshipArgumentRef;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Constant implements RelationshipArgumentRef {
    private int value;
}
