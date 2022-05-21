package aitsi.m3spin.query.model;


import aitsi.m3spin.commons.enums.EntityType;
import lombok.Data;

//@Getter
//@RequiredArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
@Data
public class Synonym {
    private final String name;
    private final EntityType type;
}
