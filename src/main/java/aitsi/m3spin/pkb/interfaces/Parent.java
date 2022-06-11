package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Statement;

import java.util.Set;

public interface Parent {

    Statement setParent(Statement parent, Statement child);

    Set<Statement> getParentedBy(Statement parent);

    Statement getParent(Statement child);

    Set<Statement> getParentT(Statement child);

    Set<Statement> getParentedByT(Statement parent);

    boolean isParent(Statement parent, Statement child);

    boolean isParentT(Statement parent, Statement child);
}
