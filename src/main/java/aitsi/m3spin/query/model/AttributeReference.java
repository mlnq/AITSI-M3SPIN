package aitsi.m3spin.query.model;

import lombok.Data;

@Data
public class AttributeReference implements SelectedResult{
    private final Synonym synonym;
    private final AttributeEnum attribute;
}
