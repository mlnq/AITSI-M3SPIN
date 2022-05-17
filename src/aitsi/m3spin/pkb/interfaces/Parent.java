package aitsi.m3spin.pkb.interfaces;

import java.util.List;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;

public interface Parent {

    Statement setParent(Statement parent, Statement child);

    List<Statement> getParentedBy(Statement parent);

    Statement getParent(Statement child);

    List<Statement> getParentT(Statement child);

    List<Statement> getParentedByT(Statement parent);

    boolean isParent(Statement parent, Statement child);

    boolean isParentT(Statement parent, Statement child);
}
