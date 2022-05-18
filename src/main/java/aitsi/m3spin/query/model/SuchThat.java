package aitsi.m3spin.query.model;

import lombok.Getter;

@Getter
public class SuchThat {
    private RelationEnum relation;
    private RelationArgument firstArgument;
    private RelationArgument secondArgument;
}