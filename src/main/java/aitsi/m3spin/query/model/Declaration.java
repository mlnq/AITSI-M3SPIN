package aitsi.m3spin.query.model;


import aitsi.m3spin.commons.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Declaration {
    private String name;
    private EntityType type;
}