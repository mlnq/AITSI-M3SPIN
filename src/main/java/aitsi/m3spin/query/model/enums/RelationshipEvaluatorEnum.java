package aitsi.m3spin.query.model.enums;

import aitsi.m3spin.commons.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum RelationshipEvaluatorEnum {
    PARENT("Parent",
            Collections.singleton(EntityType.STATEMENT),
            Collections.singleton(EntityType.STATEMENT)),
    PARENT_T("Parent*",
            Collections.singleton(EntityType.STATEMENT),
            Collections.singleton(EntityType.STATEMENT)),
    FOLLOWS("Follows",
            Collections.singleton(EntityType.STATEMENT),
            Collections.singleton(EntityType.STATEMENT)),
    FOLLOWS_T("Follows*",
            Collections.singleton(EntityType.STATEMENT),
            Collections.singleton(EntityType.STATEMENT)),
    MODIFIES("Modifies",
            new HashSet<>(Arrays.asList(EntityType.STATEMENT, EntityType.PROCEDURE)),
            Collections.singleton(EntityType.VARIABLE)),
    USES("Uses",
            new HashSet<>(Arrays.asList(EntityType.STATEMENT, EntityType.PROCEDURE)),
            Collections.singleton(EntityType.VARIABLE));

    private final String relationName;
    private final Set<EntityType> allowedFirstArgTypes;
    private final Set<EntityType> allowedSecondArgTypes;
}
