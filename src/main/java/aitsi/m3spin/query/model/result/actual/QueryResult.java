package aitsi.m3spin.query.model.result.actual;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.query.model.references.PrimitiveTypeReference;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class QueryResult {
    public static QueryResult ofBoolean(boolean booleanResult) {
        return new BooleanResult(booleanResult);
    }

    public static QueryResult ofTNodeSet(Set<? extends TNode> tNodeSet) {
        return new TNodeSetResult(tNodeSet);
    }

    public static QueryResult ofAttrList(List<PrimitiveTypeReference> attrList) {
        return new AttrListResult(attrList);
    }

    public abstract boolean isTrue();
}
