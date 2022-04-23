package aitsi.m3spin.queryProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Constant implements RelationArgument {
    private int value;
}
