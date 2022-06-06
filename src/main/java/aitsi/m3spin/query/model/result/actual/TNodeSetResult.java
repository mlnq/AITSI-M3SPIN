package aitsi.m3spin.query.model.result.actual;

import aitsi.m3spin.commons.interfaces.TNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class TNodeSetResult extends QueryResult {
    private final Set<? extends TNode> result;

    public static TNodeSetResult empty() {
        return new TNodeSetResult(Collections.emptySet());
    }

    @Override
    public boolean isTrue() {
        return !result.isEmpty();
    }
}
