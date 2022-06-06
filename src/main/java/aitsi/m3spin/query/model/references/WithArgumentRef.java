package aitsi.m3spin.query.model.references;

import aitsi.m3spin.query.model.enums.AttributeTypeEnum;
import aitsi.m3spin.query.model.enums.WithArgRefType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class WithArgumentRef {
    private final AttributeTypeEnum withValueType;
    private final WithArgRefType withArgRefType;

    public abstract boolean equalsToSynonym(Synonym synonym);
}
