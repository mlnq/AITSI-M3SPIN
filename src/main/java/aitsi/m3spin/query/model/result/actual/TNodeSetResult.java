package aitsi.m3spin.query.model.result.actual;

import aitsi.m3spin.commons.interfaces.TNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class TNodeSetResult extends QueryResult {
    private final Set<? extends TNode> result;
}
