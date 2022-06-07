package aitsi.m3spin.commons.interfaces;

import aitsi.m3spin.pkb.model.AttributableNode;

public interface Statement extends TNode, AttributableNode {
    int getProgLine();

    void setProgLine(int progLine);
}
