package aitsi.m3spin.commons.interfaces;

import aitsi.m3spin.pkb.model.AttributableNode;

public interface Call extends Statement, AttributableNode {

    String getProcName();

    void setProcName(String procName);
}
