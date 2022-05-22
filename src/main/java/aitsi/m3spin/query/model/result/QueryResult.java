package aitsi.m3spin.query.model.result;

import aitsi.m3spin.commons.interfaces.TNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryResult {
    public static QueryResult ofBoolean(boolean booleanResult) {
        return new BooleanResult(booleanResult);
    }

    public static QueryResult ofTNodeSet(Set<TNode> tNodeSet) {
        return new TNodeSetResult(tNodeSet);
    }

    public static QueryResult ofAttrList(List<String> attrList) {
        return new AttrListResult(attrList);
    }
}
