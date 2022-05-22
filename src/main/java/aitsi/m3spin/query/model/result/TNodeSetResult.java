package aitsi.m3spin.query.model.result;

import aitsi.m3spin.commons.interfaces.TNode;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class TNodeSetResult extends QueryResult {
    private final Set<TNode> result;
}
