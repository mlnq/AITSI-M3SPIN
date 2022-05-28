package aitsi.m3spin.pkb.model;

import aitsi.m3spin.commons.interfaces.NodeAttribute;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class IntegerAttribute implements NodeAttribute {
    private final Integer value;
}
