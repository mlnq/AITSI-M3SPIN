package aitsi.m3spin.tests;

import aitsi.m3spin.commons.impl.StatementImpl;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.impl.ParentImpl;
import aitsi.m3spin.pkb.interfaces.Parent;
import org.junit.jupiter.api.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParentImplTest {
    private final Parent parentMethods = new ParentImpl();

    @Test
    void setParent() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();
        child.setParent(parent);

        assertEquals(parentMethods.getParent(child), parent);
    }

    @Test
    void getParentedBy() {

        Statement parent = new StatementImpl();
        Statement firstChild = new StatementImpl();
        Statement secondChild = new StatementImpl();
        Statement thirdChild = new StatementImpl();

        //first child
        firstChild.setParent(parent);
        parent.setChild(firstChild);

        //second child
        firstChild.setRightSibling(secondChild);
        secondChild.setLeftSibling(firstChild);

        //third child
        secondChild.setRightSibling(thirdChild);
        thirdChild.setLeftSibling(secondChild);

        List<Statement> childList =new ArrayList<Statement>(){{
            add(firstChild);
            add(secondChild);
            add(thirdChild);
        }};

        assertAll(
                () -> assertEquals(parentMethods.getParentedBy(parent).size(), 3),
                () -> assertTrue(parentMethods.getParentedBy(parent).containsAll(childList))
        );

    }

    @Test
    void getParent() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();
        child.setParent(parent);

        assertEquals(parentMethods.getParent(child), parent);
    }

    @Test
    void getParentT() {
        throw new NotImplementedException();
    }

    @Test
    void getParentedByT() {
        throw new NotImplementedException();
    }

    @Test
    void isParent() {
        Statement child =  new StatementImpl();
        Statement parent = new StatementImpl();
        child.setParent(parent);

        assertEquals(parentMethods.getParent(child), parent);
    }

    @Test
    void isParentT() {
        throw new NotImplementedException();
    }
}