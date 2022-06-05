package aitsi.m3spin.pkb;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.commons.interfaces.While;
import aitsi.m3spin.pkb.exception.IllegalLinkTypeException;
import aitsi.m3spin.pkb.exception.IllegalNodeTypeException;
import aitsi.m3spin.pkb.impl.AstImpl;
import aitsi.m3spin.pkb.interfaces.Ast;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstImplTest {

    private final Ast ast = new AstImpl();

    @Test
    void createTNode_EntityTypeAssignment_NodeIsCreatedAndTypeIsSetAsAssignment() throws IllegalNodeTypeException {
        assertEquals(EntityType.ASSIGNMENT, ast.createTNode(EntityType.ASSIGNMENT).getType());
    }

    @Test
    void createTNode_EntityTypeProcedure_NodeIsCreatedAndTypeIsSetAsProcedure() throws IllegalNodeTypeException {
        assertEquals(EntityType.PROCEDURE, ast.createTNode(EntityType.PROCEDURE).getType());
    }

    @Test
    void createTNode_EntityTypeExpression_ThrowsIllegalNodeTypeException() {
        assertThrows(IllegalNodeTypeException.class, () -> ast.createTNode(EntityType.EXPRESSION));
    }

    @Test
    void setRoot_CorrectRoot_SetsAndReturnsRoot() {
        While root = new WhileImpl();

        assertEquals(root, ast.setRoot(root));
        assertEquals(root, ast.getRoot());
    }

    @Test
    void setRoot_RootSetAsNull_SetsAndReturnsNullAsRoot() {
        assertNull(ast.setRoot(null));
    }

    @Test
    void setVariableName_SetNameAsX_NameIsSetAsX() {
        Variable variable = new VariableImpl(0);
        ast.setName(variable, "x");

        assertEquals("x", variable.getAttribute());
    }

    @Test
    void setVariableName_SetNameAsNull_NameIsSetAsNull() {
        Variable variable = new VariableImpl(0);
        ast.setName(variable, null);

        assertNull(variable.getAttribute());
    }

    @Test
    void setChild_CorrectNode_ChildIsSetAndReturned() {
        Statement parent = new WhileImpl();
        Statement child = new AssignmentImpl();

        assertEquals(child, ast.setChild(parent, child));
        assertEquals(child, parent.getChild());
    }

    @Test
    void setChild_ChildEqualNull_ThrowsNullPointerException() {
        Statement parent = new WhileImpl();
        assertThrows(NullPointerException.class, () -> ast.setChild(parent, null));
    }

    @Test
    void setSibling_CorrectNodes_SiblingIsSetAccordinglyForBothNodes() {
        Statement left = new AssignmentImpl();
        Statement right = new AssignmentImpl();

        assertEquals(left, ast.setSibling(left, right));
        assertEquals(right, left.getRightSibling());
        assertEquals(left, right.getLeftSibling());
    }

    @Test
    void setSibling_OneSiblingIsNull_ThrowsNullPointerException() {
        Statement left = new WhileImpl();
        assertThrows(NullPointerException.class, () -> ast.setSibling(left, null));
    }

    @Test
    void setLink_SetParent_SetsParentAndChildAndReturnsParent() throws IllegalLinkTypeException {
        Statement parent = new WhileImpl();
        Statement child = new AssignmentImpl();

        assertEquals(parent, ast.setLink(LinkType.PARENT, parent, child));
        assertEquals(parent, child.getParent());
        assertEquals(child, parent.getChild());
    }

    @Test
    void setLink_SetSibling_SetsSiblingsAndReturnsTheFirstOne() throws IllegalLinkTypeException {
        Statement firstSibling = new AssignmentImpl();
        Statement secondSibling = new WhileImpl();

        assertEquals(secondSibling, ast.setLink(LinkType.SIBLING, firstSibling, secondSibling));
        assertEquals(firstSibling, secondSibling.getLeftSibling());
        assertEquals(secondSibling, firstSibling.getRightSibling());
    }

    @Test
    void setLink_SetChild_SetsChildAndParentAndReturnsChild() throws IllegalLinkTypeException {
        Statement parent = new WhileImpl();
        Statement child = new AssignmentImpl();

        assertEquals(parent, ast.setLink(LinkType.CHILD, child, parent));
        assertEquals(parent, child.getParent());
        assertEquals(child, parent.getChild());
    }

    @Test
    void setLink_SetIncorrectRelationType_ThrowsIllegalLinkTypeException() {
        Statement parent = new AssignmentImpl();
        Statement child = new AssignmentImpl();

        assertThrows(IllegalLinkTypeException.class, () -> ast.setLink(LinkType.FOLLOWS, child, parent));
    }


    @Test
    void getRoot_RootIsSet_ReturnsNode() {
        Statement root = new WhileImpl();
        ast.setRoot(root);

        assertEquals(root, ast.getRoot());
    }

    @Test
    void getRoot_RootIsNotSet_ReturnsNull() {
        assertNull(ast.getRoot());
    }

    @Test
    void getType_NodeOfVariableType_ReturnsVariableEntityType() {
        Variable variable = new VariableImpl(0);
        assertEquals(EntityType.VARIABLE, ast.getType(variable));
    }

    @Test
    void getType_NodeOfAssignmentType_ReturnsAssignmentEntityType() {
        Assignment assignment = new AssignmentImpl();
        assertEquals(EntityType.ASSIGNMENT, ast.getType(assignment));
    }

    @Test
    void getType_GetNodeTypeOfNull_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ast.getType(null));
    }

    @Test
    void getName_VariableWithAttributeY_ReturnsY() {
        Variable variable = new VariableImpl(0);
        variable.setAttribute("y");
        assertEquals("y", ast.getName(variable));
    }

    @Test
    void getName_VariableWithAttributeSetAsNull_ReturnsNull() {
        Variable variable = new VariableImpl(0);
        variable.setAttribute(null);
        assertNull(ast.getName(variable));
    }

    @Test
    void getChild_ChildIsSet_ReturnsChild() {
        Statement parent = new WhileImpl();
        Statement child = new IfImpl();
        parent.setChild(child);
        assertEquals(child, ast.getChild(parent));
    }

    @Test
    void getChild_ChildIsNotSet_ReturnsNull() {
        Statement parent = new IfImpl();
        assertNull(ast.getChild(parent));
    }

    @Test
    void getLinkedNode_LinkedByParentRelation_ReturnsLinkedChild() throws IllegalLinkTypeException {
        Statement child = new AssignmentImpl();
        Statement parent = new IfImpl();

        ast.setLink(LinkType.PARENT, child, parent);
        assertEquals(child, ast.getLinkedNode(LinkType.PARENT, parent));
    }

    @Test
    void getLinkedNode_LinkedBySiblingRelation_ReturnsSecondSibling() throws IllegalLinkTypeException {
        Statement firstSibling = new AssignmentImpl();
        Statement secondSibling = new IfImpl();

        ast.setLink(LinkType.SIBLING, firstSibling, secondSibling);
        assertEquals(secondSibling, ast.getLinkedNode(LinkType.SIBLING, firstSibling));
    }

    @Test
    void getLinkedNode_LinkedByChildRelation_ReturnsParent() throws IllegalLinkTypeException {
        Statement parent = new IfImpl();
        Statement child = new WhileImpl();

        ast.setLink(LinkType.CHILD, parent, child);
        assertEquals(parent, ast.getLinkedNode(LinkType.CHILD, child));
    }

    @Test
    void getLinkedNode_IllegalRelationType_ThrowsIllegalLinkTypeException() {
        Statement child = new AssignmentImpl();

        assertThrows(IllegalLinkTypeException.class, () -> ast.getLinkedNode(LinkType.FOLLOWS, child));
    }

    @Test
    void isLinked_ParentRelation_ReturnsTrue() throws IllegalLinkTypeException {
        Statement parent = new IfImpl();
        Statement child = new WhileImpl();

        ast.setLink(LinkType.PARENT, parent, child);
        assertTrue(ast.isLinked(LinkType.PARENT, child, parent));
    }

    @Test
    void isLinked_SiblingRelation_ReturnsTrue() throws IllegalLinkTypeException {
        Statement firstSibling = new AssignmentImpl();
        Statement secondSibling = new IfImpl();

        ast.setLink(LinkType.SIBLING, firstSibling, secondSibling);
        assertTrue(ast.isLinked(LinkType.SIBLING, firstSibling, secondSibling));
    }

    @Test
    void isLinked_ChildRelation_ReturnsTrue() throws IllegalLinkTypeException {
        Statement child = new AssignmentImpl();
        Statement parent = new WhileImpl();

        ast.setLink(LinkType.CHILD, child, parent);
        assertTrue(ast.isLinked(LinkType.CHILD, parent, child));
    }

    @Test
    void isLinked_IllegalRelation_ThrowsIllegalLinkTypeException() {
        Statement child = new WhileImpl();
        Statement parent = new IfImpl();

        assertThrows(IllegalLinkTypeException.class, () -> ast.isLinked(LinkType.FOLLOWS, parent, child));
    }
}
