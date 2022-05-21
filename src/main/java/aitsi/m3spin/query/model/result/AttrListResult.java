package aitsi.m3spin.query.model.result;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AttrListResult extends QueryResult {
    private final List<String> attrList;
}
