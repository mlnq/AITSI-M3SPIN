package aitsi.m3spin.pkb;

import aitsi.m3spin.commons.impl.StatementImpl;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.impl.ParentImpl;
import aitsi.m3spin.pkb.interfaces.Parent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParentImplTest {
    private final Parent parentMethods = new ParentImpl();

    @Test
    void setParent_SettingCorrectNode_SetsAndReturnsParent() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();

        assertAll(
                () -> assertEquals(parent, parentMethods.setParent(parent, child)),
                () -> assertEquals(parent, parentMethods.getParent(child))
        );
    }

    @Test
    void setParent_SettingNull_ThrowsNullPointerExceptionOnGetParent() {
        Statement child = new StatementImpl();
        assertThrows(NullPointerException.class, () -> parentMethods.setParent(null, child));
    }

    @Test
    void getParentedBy_ParentWithThreeChildren_ReturnsThreeChildren() {
        Statement parent = new StatementImpl();
        Statement firstChild = new StatementImpl();
        Statement secondChild = new StatementImpl();
        Statement thirdChild = new StatementImpl();

        firstChild.setParent(parent);
        parent.setChild(firstChild);

        firstChild.setRightSibling(secondChild);
        secondChild.setLeftSibling(firstChild);

        secondChild.setRightSibling(thirdChild);
        thirdChild.setLeftSibling(secondChild);

        List<Statement> childList = new ArrayList<Statement>() {{
            add(firstChild);
            add(secondChild);
            add(thirdChild);
        }};

        assertTrue(parentMethods.getParentedBy(parent).containsAll(childList));
    }

    @Test
    void getParentedBy_CheckingNullNode_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parentMethods.getParentedBy(null));
    }

    @Test
    void getParent_CorrectParent_ReturnsSetParent() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();
        child.setParent(parent);
        assertEquals(parent, parentMethods.getParent(child));
    }

    @Test
    void getParent_NoParent_ThrowsNullPointerException() {
        Statement child = new StatementImpl();
        assertThrows(NullPointerException.class, () -> parentMethods.getParent(child));
    }

    @Test
    void isParent_CorrectPairOfParentAndChild_ReturnsTrue() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();
        child.setParent(parent);
        assertTrue(parentMethods.isParent(parent, child));
    }

    @Test
    void isParent_PairOfIndependentNodes_ThrowsNullPointerException() {
        Statement child = new StatementImpl();
        Statement parent = new StatementImpl();
        assertThrows(NullPointerException.class, () -> parentMethods.isParent(parent, child));
    }

    @Test
    void isParent_PairOfNullValues_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parentMethods.isParent(null, null));
    }


    /*
    @Test
    void getParentT() {
        throw new NotImplementedException();
    }

    @Test
    void getParentedByT() {
        throw new NotImplementedException();
    }

    @Test
    void isParentT() {
        throw new NotImplementedException();
    }*/
}