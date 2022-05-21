package aitsi.m3spin.query.evaluator;

import aitsi.m3spin.commons.interfaces.TNode;

import java.util.Set;

public class QueryResult {
    public static of(boolean booleanResult);
    public static of(Set<TNode> tNodeSet);
}
