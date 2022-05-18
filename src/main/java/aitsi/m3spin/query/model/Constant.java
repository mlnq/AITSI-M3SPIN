package aitsi.m3spin.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Constant implements RelationArgument {
    private int value;
}