package aitsi.m3spin.query.model.result.actual;

import aitsi.m3spin.query.model.references.PrimitiveTypeReference;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AttrListResult extends QueryResult {
    private final List<PrimitiveTypeReference> attrList;

    @Override
    public boolean isTrue() {
        return !attrList.isEmpty();
    }
}
