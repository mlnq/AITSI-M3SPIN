package aitsi.m3spin.pkb;

import aitsi.m3spin.commons.Attr;
import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.pkb.interfaces.*;

import java.util.List;

public class PkbImpl implements Ast, VarTable, ProcTable, Parent, Follows, Modifies, Uses {
    @Override
    public TNode createTNode(EntityType et) {
        return null;
    }

    @Override
    public void setAttr(TNode n, Attr attr) {

    }

    @Override
    public TNode setChild(TNode parent, TNode child) {
        return child;
    }

    @Override
    public TNode setRightSibling(TNode l, TNode r) {
        return r;
    }

    @Override
    public void setLeftSibling(TNode l, TNode r) {

    }

    @Override
    public void setChildOfLink(TNode parent, TNode child) {

    }

    @Override
    public void setLink(LinkType relation, TNode node1, TNode node2) {

    }

    @Override
    public TNode getRoot() {
        return null;
    }

    @Override
    public void setRoot(TNode node) {

    }

    @Override
    public EntityType getType(TNode node) {
        return null;
    }

    @Override
    public Attr getAttr(TNode node) {
        return null;
    }

    @Override
    public TNode getFirstChild(TNode p) {
        return null;
    }

    @Override
    public TNode getLinkedNode(LinkType link, TNode node1) {
        return null;
    }

    @Override
    public Boolean isLinked(LinkType link, TNode node1, TNode node2) {
        return null;
    }

    @Override
    public void setParent(TNode p, TNode c) {

    }

    @Override
    public TNode getParent(TNode c) {
        return null;
    }

    @Override
    public List<TNode> getParentedBy(TNode p) {
        return null;
    }

    @Override
    public TNode getParentT(TNode c) {
        return null;
    }

    @Override
    public List<TNode> getParentedTBy(TNode p) {
        return null;
    }

    @Override
    public void setFollows(TNode p, TNode c) {

    }

    @Override
    public TNode getFollows(TNode n) {
        return null;
    }

    @Override
    public List<TNode> getFollowsT(TNode n) {
        return null;
    }

    @Override
    public TNode getFollowedBy(TNode n) {
        return null;
    }

    @Override
    public List<TNode> getFollowedTBy(TNode n) {
        return null;
    }

    @Override
    public Boolean isFollowed(TNode n1, TNode n2) {
        return null;
    }

    @Override
    public Boolean isFollowedT(TNode n1, TNode n2) {
        return null;
    }

    @Override
    public Boolean isParent(TNode p, TNode c) {
        return null;
    }

    @Override
    public Boolean isParentT(TNode p, TNode c) {
        return null;
    }

    @Override
    public Index insertVar(String varName) {
        return null;
    }

    @Override
    public String getVarName(Index index) {
        return null;
    }

    @Override
    public Index getVarIndex(String varName) {
        return null;
    }

    @Override
    public Integer getVarTableSize() {
        return null;
    }

    @Override
    public Boolean isInVarTable(String varName) {
        return null;
    }

    @Override
    public Index insertProc(String procName) {
        return null;
    }

    @Override
    public String getProcName(Index index) {
        return null;
    }

    @Override
    public Index getProcIndex(String procName) {
        return null;
    }

    @Override
    public Integer getProcTableSize() {
        return null;
    }

    @Override
    public Boolean isInProcTable(String procName) {
        return null;
    }

    @Override
    public void setModifies(Statement stmt, Variable variable) {

    }

    @Override
    public void setModifies(Procedure proc, Variable variable) {

    }

    @Override
    public List<Variable> getModified(Statement stmt) {
        return null;
    }

    @Override
    public List<Variable> getModified(Procedure proc) {
        return null;
    }

    @Override
    public List<Statement> getModifiesSTMT(Variable variable) {
        return null;
    }

    @Override
    public List<Procedure> getModifiesPROC(Variable variable) {
        return null;
    }

    @Override
    public Boolean isModified(Variable variable, Statement stmt) {
        return null;
    }

    @Override
    public Boolean isModified(Variable variable, Procedure proc) {
        return null;
    }

    @Override
    public void setUses(Statement stmt, Variable variable) {

    }

    @Override
    public void setUses(Procedure proc, Variable variable) {

    }

    @Override
    public List<Variable> getUses(Statement stmt) {
        return null;
    }

    @Override
    public List<Variable> getUses(Procedure proc) {
        return null;
    }

    @Override
    public List<Statement> getUsesSTMT(Variable variable) {
        return null;
    }

    @Override
    public List<Procedure> getUsesPROC(Variable variable) {
        return null;
    }

    @Override
    public Boolean isUsed(Variable variable, Statement stat) {
        return null;
    }

    @Override
    public Boolean isUseded(Variable variable, Procedure proc) {
        return null;
    }
}
