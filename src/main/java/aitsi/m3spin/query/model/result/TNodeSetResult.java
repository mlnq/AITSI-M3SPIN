package aitsi.m3spin.query.model.result;

import aitsi.m3spin.commons.interfaces.TNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class TNodeSetResult extends QueryResult {
    private final Set<? extends TNode> result;
}
