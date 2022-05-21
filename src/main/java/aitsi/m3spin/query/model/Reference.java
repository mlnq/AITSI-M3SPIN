package aitsi.m3spin.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Reference {
    private final ReferenceType type;

    public abstract boolean equalsToSynonym(Synonym synonym);
}
