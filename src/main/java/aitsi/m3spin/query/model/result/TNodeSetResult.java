package aitsi.m3spin.query.model.result;

import aitsi.m3spin.commons.interfaces.TNode;
import lombok.Data;

import java.util.Set;

@Data
public class TNodeSetResult extends QueryResult {
    private final Set<TNode> result;
}
