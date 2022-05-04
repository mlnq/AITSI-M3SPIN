package aitsi.m3spin.pkb.interfaces;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;

import java.util.List;

public interface Parent {

    Statement setParent(Statement parent, Statement child);

    List<Statement> getParentedBy(Statement parent);

    Statement getParent(Statement child);

    List<Statement> getParentT(Statement child);

    List<Statement> getParentedByT(Statement parent);

    Boolean isParent(Statement parent, Statement child);

    Boolean isParentT(Statement parent, Statement child);
}
