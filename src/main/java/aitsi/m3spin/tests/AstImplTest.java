package aitsi.m3spin.tests;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.enums.LinkType;
import aitsi.m3spin.commons.impl.AssignmentImpl;
import aitsi.m3spin.commons.impl.StatementImpl;
import aitsi.m3spin.commons.impl.VariableImpl;
import aitsi.m3spin.commons.interfaces.Assignment;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.pkb.exception.IllegalNodeTypeException;
import aitsi.m3spin.pkb.impl.AstImpl;
import aitsi.m3spin.pkb.interfaces.Ast;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import static org.junit.jupiter.api.Assertions.*;

class AstImplTest {

    private final Ast ast = new AstImpl();

    @Test
    void createTNode() {

        try
        {
            assertAll(
                    () -> assertEquals(EntityType.ASSIGNMENT, ast.createTNode(EntityType.ASSIGNMENT).getType()),
                    () -> assertEquals(EntityType.CONSTANT, ast.createTNode(EntityType.CONSTANT).getType()),
                    () -> assertEquals(EntityType.IF, ast.createTNode(EntityType.IF).getType()),
                    () -> assertEquals(EntityType.MINUS, ast.createTNode(EntityType.MINUS).getType()),
                    () -> assertEquals(EntityType.PLUS, ast.createTNode(EntityType.PLUS).getType()),
                    () -> assertEquals(EntityType.PROCEDURE, ast.createTNode(EntityType.PROCEDURE).getType()),
                    () -> assertEquals(EntityType.VARIABLE, ast.createTNode(EntityType.VARIABLE).getType()),
                    () -> assertEquals(EntityType.WHILE, ast.createTNode(EntityType.WHILE).getType()),
                    () -> assertThrows(IllegalNodeTypeException.class, () -> ast.createTNode(EntityType.EXPRESSION)),
                    () -> assertThrows(IllegalNodeTypeException.class, () -> ast.createTNode(EntityType.STMT)),
                    () -> assertThrows(IllegalNodeTypeException.class, () -> ast.createTNode(EntityType.STMT_LIST))
            );
        }
        catch (Exception e) {
            throw new TestAbortedException();
        }

    }

    @Test
    void setRoot() {
        Statement root = new StatementImpl();
        ast.setRoot(root);

        assertEquals(ast.getRoot(), root);
    }

    @Test
    void setName() {
        Variable variable = new VariableImpl(0);
        ast.setName(variable, "x");

        assertEquals("x", variable.getAttribute());
    }

    @Test
    void setChild() {
        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();
        ast.setChild(parent, child);

        assertEquals(parent.getChild(), child);
    }

    @Test
    void setSibling() {
        Statement left = new StatementImpl();
        Statement right = new StatementImpl();

        ast.setSibling(left, right);

        assertAll(
                () -> assertEquals(left.getRightSibling(), right),
                () -> assertEquals(right.getLeftSibling(), left)
        );
    }

    @Test
    void setLink() {

        Statement node1 = new StatementImpl();
        Statement node2 = new StatementImpl();
        Statement node3 = new StatementImpl();
        Statement node4 = new StatementImpl();

        try
        {
            ast.setLink(LinkType.PARENT, node1, node2);
            ast.setLink(LinkType.SIBLING, node2, node3);
            ast.setLink(LinkType.CHILD, node4, node3);
        }
        catch (Exception e){
            throw new TestAbortedException();
        }

        assertAll(
                () -> assertEquals(node1, node2.getParent()),
                () -> assertEquals(node3, node2.getRightSibling()),
                () -> assertEquals(node4, node3.getChild())
        );
    }

    @Test
    void getRoot() {
        Statement root = new StatementImpl();
        ast.setRoot(root);

        assertEquals(root, ast.getRoot());
    }

    @Test
    void getType() {
        Variable variable = new VariableImpl(0);
        Assignment assignment = new AssignmentImpl();

        assertAll(
                () -> assertEquals(EntityType.VARIABLE, ast.getType(variable)),
                () -> assertEquals(EntityType.ASSIGNMENT, ast.getType(assignment))
        );
    }

    @Test
    void getName() {
        Variable variable = new VariableImpl(0);
        ast.setName(variable, "y");

        assertEquals("y", ast.getName(variable));
    }

    @Test
    void getChild() {
        Statement parent = new StatementImpl();
        Statement child = new StatementImpl();
        parent.setChild(child);

        assertEquals(child, ast.getChild(parent));
    }

    @Test
    void getLinkedNode() {
        Statement node1 = new StatementImpl();
        Statement node2 = new StatementImpl();
        Statement node3 = new StatementImpl();
        Statement node4 = new StatementImpl();

        try
        {
            ast.setLink(LinkType.PARENT, node1, node2);
            ast.setLink(LinkType.SIBLING, node2, node3);
            ast.setLink(LinkType.CHILD, node4, node3);
        }
        catch (Exception e){
            throw new TestAbortedException();
        }

        assertAll(
                () -> assertEquals(node3 ,ast.getLinkedNode(LinkType.PARENT, node4)),
                () -> assertEquals(node1, ast.getLinkedNode(LinkType.PARENT, node2)),
                () -> assertEquals(node4 ,ast.getLinkedNode(LinkType.CHILD, node3)),
                () -> assertNull(ast.getLinkedNode(LinkType.CHILD, node4)),
                () -> assertEquals(node3 ,ast.getLinkedNode(LinkType.SIBLING, node2)),
                () -> assertNull(ast.getLinkedNode(LinkType.SIBLING, node1))
        );
    }

    @Test
    void isLinked() {
        Statement node1 = new StatementImpl();
        Statement node2 = new StatementImpl();
        Statement node3 = new StatementImpl();
        Statement node4 = new StatementImpl();

        try {
            ast.setLink(LinkType.PARENT, node1, node2);
            ast.setLink(LinkType.SIBLING, node2, node3);
            ast.setLink(LinkType.CHILD, node4, node3);
        }
        catch (Exception e) {
            throw new TestAbortedException();
        }

        assertAll(
                () -> assertTrue(ast.isLinked(LinkType.PARENT, node2, node1)),
                () -> assertFalse(ast.isLinked(LinkType.PARENT, node1, node2)),
                () -> assertTrue(ast.isLinked(LinkType.SIBLING, node2, node3)),
                () -> assertFalse(ast.isLinked(LinkType.SIBLING, node3, node1)),
                () -> assertTrue(ast.isLinked(LinkType.CHILD, node1, node2)),
                () -> assertFalse(ast.isLinked(LinkType.CHILD, node4, node3))
        );
    }
}