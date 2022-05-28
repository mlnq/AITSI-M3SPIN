package aitsi.m3spin.pkb;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.impl.AssignmentImpl;
import aitsi.m3spin.commons.impl.StatementImpl;
import aitsi.m3spin.commons.impl.VariableImpl;
import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.exception.IllegalLinkTypeException;
import aitsi.m3spin.pkb.exception.IllegalNodeTypeException;
import aitsi.m3spin.pkb.impl.AstImpl;
import aitsi.m3spin.pkb.interfaces.Ast;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import static org.junit.jupiter.api.Assertions.*;

class AstImplTest {

    private final Ast ast = new AstImpl();

    @Test
    void createTNode_EntityTypeAssignment_NodeIsCreatedAndTypeIsSetAsAssignment() {
        try {
            assertEquals(EntityType.ASSIGNMENT, ast.createTNode(EntityType.ASSIGNMENT).getType());
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void createTNode_EntityTypeProcedure_NodeIsCreatedAndTypeIsSetAsProcedure() {
        try {
            assertEquals(EntityType.PROCEDURE, ast.createTNode(EntityType.PROCEDURE).getType());
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void createTNode_EntityTypeExpression_ThrowsIllegalNodeTypeException() {
        try {
            assertThrows(IllegalNodeTypeException.class, () -> ast.createTNode(EntityType.EXPRESSION));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void setRoot_CorrectRoot_SetsAndReturnsRoot() {
        Statement root = new StatementImpl();

        assertAll(
                () -> assertEquals(root, ast.setRoot(root)),
                () -> assertEquals(root, ast.getRoot())
        );
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
        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();

        assertAll(
                () -> assertEquals(child, ast.setChild(parent, child)),
                () -> assertEquals(child, parent.getChild())
        );
    }

    @Test
    void setChild_ChildEqualNull_ThrowsNullPointerException() {
        Statement parent = new StatementImpl();
        assertThrows(NullPointerException.class, () -> ast.setChild(parent, null));
    }

    @Test
    void setSibling_CorrectNodes_SiblingIsSetAccordinglyForBothNodes() {
        Statement left = new StatementImpl();
        Statement right = new StatementImpl();

        assertAll(
                () -> assertEquals(left, ast.setSibling(left, right)),
                () -> assertEquals(right, left.getRightSibling()),
                () -> assertEquals(left, right.getLeftSibling())
        );
    }

    @Test
    void setSibling_OneSiblingIsNull_ThrowsNullPointerException() {
        Statement left = new StatementImpl();
        assertThrows(NullPointerException.class, () -> ast.setSibling(left, null));

    }

    @Test
    void setLink_SetParent_SetsParentAndChildAndReturnsParent() {

        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();

        try {
            assertAll(
                    () -> assertEquals(parent, ast.setLink(LinkType.PARENT, parent, child)),
                    () -> assertEquals(parent, child.getParent()),
                    () -> assertEquals(child, parent.getChild())
            );
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void setLink_SetSibling_SetsSiblingsAndReturnsTheFirstOne() {

        Statement firstSibling = new StatementImpl();
        Statement secondSibling = new StatementImpl();

        try {
            assertAll(
                    () -> assertEquals(firstSibling, ast.setLink(LinkType.SIBLING, firstSibling, secondSibling)),
                    () -> assertEquals(firstSibling, secondSibling.getLeftSibling()),
                    () -> assertEquals(secondSibling, firstSibling.getRightSibling())
            );
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void setLink_SetChild_SetsChildAndParentAndReturnsChild() {

        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();

        try {
            assertAll(
                    () -> assertEquals(parent, ast.setLink(LinkType.CHILD, child, parent)),
                    () -> assertEquals(parent, child.getParent()),
                    () -> assertEquals(child, parent.getChild())
            );
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void setLink_SetIncorrectRelationType_ThrowsIllegalLinkTypeException() {

        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();

        try {
            assertThrows(IllegalLinkTypeException.class, () -> ast.setLink(LinkType.FOLLOWS, child, parent));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }


    @Test
    void getRoot_RootIsSet_ReturnsNode() {
        Statement root = new StatementImpl();
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
        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();
        parent.setChild(child);
        assertEquals(child, ast.getChild(parent));
    }

    @Test
    void getChild_ChildIsNotSet_ReturnsNull() {
        Statement parent = new StatementImpl();
        assertNull(ast.getChild(parent));
    }

    @Test
    void getLinkedNode_LinkedByParentRelation_ReturnsLinkedChild() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();

        try {
            ast.setLink(LinkType.PARENT, child, parent);
            assertEquals(child, ast.getLinkedNode(LinkType.PARENT, parent));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void getLinkedNode_LinkedBySiblingRelation_ReturnsSecondSibling() {
        Statement firstSibling = new StatementImpl();
        Statement secondSibling = new StatementImpl();

        try {
            ast.setLink(LinkType.SIBLING, firstSibling, secondSibling);
            assertEquals(secondSibling, ast.getLinkedNode(LinkType.SIBLING, firstSibling));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void getLinkedNode_LinkedByChildRelation_ReturnsParent() {
        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();

        try {
            ast.setLink(LinkType.CHILD, parent, child);
            assertEquals(parent, ast.getLinkedNode(LinkType.CHILD, child));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void getLinkedNode_IllegalRelationType_ThrowsIllegalLinkTypeException() {
        Statement child = new StatementImpl();

        try {
            assertThrows(IllegalLinkTypeException.class, () -> ast.getLinkedNode(LinkType.FOLLOWS, child));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void isLinked_ParentRelation_ReturnsTrue() {
        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();


        try {
            ast.setLink(LinkType.PARENT, parent, child);
            assertTrue(ast.isLinked(LinkType.PARENT, child, parent));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void isLinked_SiblingRelation_ReturnsTrue() {
        Statement firstSibling = new StatementImpl();
        Statement secondSibling = new StatementImpl();

        try {
            ast.setLink(LinkType.SIBLING, firstSibling, secondSibling);
            assertTrue(ast.isLinked(LinkType.SIBLING, firstSibling, secondSibling));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void isLinked_ChildRelation_ReturnsTrue() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();

        try {
            ast.setLink(LinkType.CHILD, child, parent);
            assertTrue(ast.isLinked(LinkType.CHILD, parent, child));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }

    @Test
    void isLinked_IllegalRelation_ThrowsIllegalLinkTypeException() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();

        try {
            assertThrows(IllegalLinkTypeException.class, () -> ast.isLinked(LinkType.FOLLOWS, parent, child));
        } catch (Exception e) {
            throw new TestAbortedException();
        }
    }
}
