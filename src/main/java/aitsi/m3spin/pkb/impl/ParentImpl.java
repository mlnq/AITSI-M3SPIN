package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.interfaces.Parent;
import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ParentImpl implements Parent {
    HashMap<Statement, HashSet<Statement>> parents = new HashMap<>();

    @Override
    public Statement setParent(@NonNull Statement parent, @NonNull Statement child) {
        if (parents.containsKey(parent)) {
            parents.get(parent).add(child);
        } else {
            parents.computeIfAbsent(parent, k -> new HashSet<>(Collections.singletonList(child)));
        }
        return parent;
    }

    @Override
    public Set<Statement> getParentedBy(Statement parent) {
        HashSet<Statement> children = parents.get(parent);
        if (children == null) {
            return Collections.emptySet();
        } else {
            return new HashSet<>(children);
        }
    }

    @Override
    public Statement getParent(Statement child) {
        return parents.keySet().stream()
                .filter(parent -> parents.get(parent).contains(child))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<Statement> getParentT(Statement child) {
        return Collections.emptySet();
    }

    @Override
    public Set<Statement> getParentedByT(Statement parent) {
        return Collections.emptySet();
    }

    @Override
    public boolean isParent(@NonNull Statement parent, @NonNull Statement child) {
        HashSet<Statement> children = parents.get(parent);
        if (children == null) {
            return false;
        }
        return children.contains(child);
    }

    @Override
    public boolean isParentT(@NonNull Statement parent, @NonNull Statement c) {
        return false;
    }
}
