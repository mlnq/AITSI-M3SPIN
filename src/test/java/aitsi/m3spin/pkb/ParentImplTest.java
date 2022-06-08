package aitsi.m3spin.pkb;

import aitsi.m3spin.commons.impl.AssignmentImpl;
import aitsi.m3spin.commons.impl.IfImpl;
import aitsi.m3spin.commons.impl.WhileImpl;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.pkb.impl.ParentImpl;
import aitsi.m3spin.pkb.interfaces.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParentImplTest {
    private final Parent parentMethods = new ParentImpl();

    Statement whileStmt;
    Statement ifStmt;
    Statement assignmentStmt;
    Statement assignmentStmt2;

    @BeforeEach
    void beforeEach() {
        whileStmt = new WhileImpl();
        ifStmt = new IfImpl();
        assignmentStmt = new AssignmentImpl();
        assignmentStmt2 = new AssignmentImpl();
    }

    @Test
    void setParent_SettingCorrectNode_SetsAndReturnsParent() {
        assertEquals(whileStmt, parentMethods.setParent(whileStmt, ifStmt));
    }

    @Test
    void setParent_SettingNull_ThrowsNullPointerExceptionOnGetParent() {
        assertThrows(NullPointerException.class, () -> parentMethods.setParent(null, assignmentStmt));
    }

    @Test
    void getParentedBy_ParentWithThreeChildren_ReturnsThreeChildren() {
        parentMethods.setParent(whileStmt, ifStmt);

        parentMethods.setParent(whileStmt, assignmentStmt);

        parentMethods.setParent(whileStmt, assignmentStmt2);

        List<Statement> childList = new ArrayList<Statement>() {{
            add(ifStmt);
            add(assignmentStmt);
            add(assignmentStmt2);
        }};

        assertTrue(parentMethods.getParentedBy(whileStmt).containsAll(childList));
    }

    @Test
    void getParentedBy_CheckingNullNode_ReturnsEmptyList() {
        assertEquals(Collections.emptySet(), parentMethods.getParentedBy(null));
    }

    @Test
    void getParent_CorrectParent_ReturnsSetParent() {
        parentMethods.setParent(whileStmt, assignmentStmt2);
        assertEquals(whileStmt, parentMethods.getParent(assignmentStmt2));
    }

    @Test
    void getParent_NoParent_returnsNull() {
        assertNull(parentMethods.getParent(assignmentStmt));
    }

    @Test
    void isParent_CorrectPairOfParentAndChild_ReturnsTrue() {
        parentMethods.setParent(whileStmt, ifStmt);
        assertTrue(parentMethods.isParent(whileStmt, ifStmt));
    }

    @Test
    void isParent_PairOfIndependentNodes_ReturnsFalse() {
        assertFalse(parentMethods.isParent(whileStmt, assignmentStmt2));
    }

    @Test
    void isParent_PairOfNullValues_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parentMethods.isParent(null, null));
    }


    /*todo testy *
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
