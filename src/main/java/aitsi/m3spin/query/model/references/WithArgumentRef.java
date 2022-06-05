package aitsi.m3spin.query.model.references;

import aitsi.m3spin.query.evaluator.exception.IncompatibleTypesComparisonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class WithArgumentRef {
    private final ReferenceType referenceType;

    public abstract boolean equalsToSynonym(Synonym synonym) throws IncompatibleTypesComparisonException;

    public abstract boolean isConstantValue();
}
