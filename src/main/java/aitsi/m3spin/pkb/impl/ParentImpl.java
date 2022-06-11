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
        Set<Statement> statements = new HashSet<>();
        Statement stm = getParent(child);
        if (stm == null)
            return Collections.emptySet();

        while (stm != null) {
            statements.add(stm);
            stm = getParent(child);
        }
        return statements;
    }

    @Override
    public Set<Statement> getParentedByT(Statement parent) {
        Set<Statement> resultStatements = new HashSet<>();
        Set<Statement> stmList = getParentedBy(parent);

        if (stmList == null)
            return Collections.emptySet();
        resultStatements.addAll(stmList);
        for (Statement stm : stmList) {
            Set<Statement> tmp = getParentedByT(stm);
            if (tmp != null)
                resultStatements.addAll(tmp);
        }
        return resultStatements;
    }

    @Override
    public boolean isParent(@NonNull Statement parent, @NonNull Statement child) {
        return getParentedBy(parent).contains(child);
    }

    @Override
    public boolean isParentT(@NonNull Statement parent, @NonNull Statement child) {
        return getParentT(child).contains(parent);
    }
}
