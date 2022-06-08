package aitsi.m3spin.pkb.impl;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.interfaces.Parent;
import lombok.Getter;

import java.util.*;

@Getter
public class ParentImpl implements Parent {
    HashMap<Statement, HashSet<Statement>> parents = new HashMap<>();
    @Override
    public Statement setParent(Statement parent, Statement child) {
        if (parents.containsKey(parent)) {
            parents.get(parent).add(child);
        } else {
            parents.computeIfAbsent(parent, k -> new HashSet<>(Collections.singletonList(child)));
        }
        return parent;
    }

    @Override
    public List<Statement> getParentedBy(Statement parent) {
        HashSet<Statement> children = parents.get(parent);
        if (children == null) {
            return null;
        } else {
            return new ArrayList<>(children);
        }
    }

    @Override
    public Statement getParent(Statement child) {
        for(Statement parent: parents.keySet()) {
            if (parents.get(parent).contains(child)) {
                return parent;
            }
        }
        return null;
    }

    @Override
    public List<Statement> getParentT(Statement child) {
        List<Statement> statements = new ArrayList<>();
        Statement stm = getParent(child);
        if(stm == null)
            return null;

        while(stm != null){
            statements.add(stm);
            stm = getParent(child);
        }
        return statements;
    }

    @Override
    public List<Statement> getParentedByT(Statement parent) {
        List<Statement> resultStatements = new ArrayList<>();
        List<Statement> stmList = getParentedBy(parent);

        if(stmList == null)
            return null;
        resultStatements.addAll(stmList);
        for(Statement stm: stmList){
            List<Statement> tmp = getParentedByT(stm);
            if(tmp != null)
                resultStatements.addAll(tmp);
        }
        return resultStatements;
    }


    @Override
    public boolean isParent(Statement parent, Statement c) {
        return c.getParent().equals(parent);
    }

    @Override
    public boolean isParentT(Statement parent, Statement child) {
        return getParentT(child).contains(parent);
    }
}
