package aitsi.m3spin.query.model.enums;

import aitsi.m3spin.query.model.references.ReferenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter


public enum RelationshipPreprocEnum {
    PARENT("Parent",
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER)),
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER))
    ),
    PARENT_T("Parent*",
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER)),
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER))
    ),
    FOLLOWS("Follows",
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER)),
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER))
    ),
    FOLLOWS_T("Follows*",
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER)),
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER))
    ),
    MODIFIES("Modifies",
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER, ReferenceType.STRING)),
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.STRING))
    ),
    USES("Uses",
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.INTEGER, ReferenceType.STRING)),
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.STRING))
    ),
    CALLS("Calls",
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.STRING)),
            new HashSet<>(Arrays.asList(ReferenceType.SYNONYM, ReferenceType.WILDCARD, ReferenceType.STRING))
    );

    private final String relationName;
    private final Set<ReferenceType> allowedFirstArgTypes;
    private final Set<ReferenceType> allowedSecondArgTypes;
}
