package aitsi.m3spin.queryProcessor.model;


import aitsi.m3spin.commons.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Declaration {
    private String name;
    private EntityType type;
}