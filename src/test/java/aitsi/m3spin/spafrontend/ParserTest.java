package aitsi.m3spin.spafrontend;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.exception.CodeScannerException;
import aitsi.m3spin.commons.impl.AssignmentImpl;
import aitsi.m3spin.commons.impl.ConstantImpl;
import aitsi.m3spin.commons.impl.WhileImpl;
import aitsi.m3spin.commons.interfaces.Factor;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.Variable;
import aitsi.m3spin.spafrontend.parser.Parser;
import aitsi.m3spin.spafrontend.parser.exception.SimpleParserException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest extends SpaFrontendTestingData {
    static List<String> codeLines;
    List<Procedure> procedures;

    @BeforeAll
    static void prepareCodeLines() {
        codeLines = new ArrayList<>();
        codeLines.add("procedure Main {");
        codeLines.add("z = x + y + k;");
        codeLines.add("a = b + c;");
        codeLines.add("while i {");
        codeLines.add("a = b + c;}");
        codeLines.add("}");
        codeLines.add(("procedure Second{"));
        codeLines.add("x = 2;");
        codeLines.add("}");
    }

    @BeforeEach
    void beforeEach() throws SimpleParserException, CodeScannerException {
        Parser parser = new Parser(codeLines);
        procedures = parser.parse();
    }

    @Test
    void parseProcedure_SimpleProcedure_Parsed() {
        Procedure parseProcedure = procedures.get(0);
        assertEquals(MAIN, parseProcedure.getProcName());
        assertEquals(EntityType.PROCEDURE, parseProcedure.getType());
        assertEquals(2, procedures.size());
    }

    @Test
    void parseStmtList_TwoStmt_Parsed() {
        Procedure firstProcedure = procedures.get(0);
        assertEquals(3, firstProcedure.getStatementList().getStatements().size());

        Procedure secondProcedure = procedures.get(1);
        assertEquals(1, secondProcedure.getStatementList().getStatements().size());
    }

    @Test
    void parseStmt_MultiParamAssignment_Parsed() {
        Statement parseAssigment = procedures.get(0).getStatementList().getStatements().get(0);
        assertEquals(EntityType.ASSIGNMENT, parseAssigment.getType());

        AssignmentImpl assignment = (AssignmentImpl) parseAssigment;
        assertEquals(Z, assignment.getVariable().getVarName());

        Variable secondFactor = (Variable) assignment.getExpression().getFactor();
        assertEquals(X, secondFactor.getVarName());

        Variable thirdFactor = (Variable) assignment.getExpression().getExpression().getFactor();
        assertEquals(Y, thirdFactor.getVarName());

        Variable fourFactor = (Variable) assignment.getExpression().getExpression().getExpression().getFactor();
        assertEquals(K, fourFactor.getVarName());
    }

    @Test
    void parseWhile_SimpleWhile_Parsed() {
        Statement parseWhile = procedures.get(0).getStatementList().getStatements().get(2);
        assertEquals(EntityType.WHILE, parseWhile.getType());

        WhileImpl whileImpl = (WhileImpl) parseWhile;
        assertEquals(I, whileImpl.getConditionVar().getVarName());
    }


    @Test
    void parseConst_SimpleConst_Parsed() {
        Statement parseAssigment = procedures.get(1).getStatementList().getStatements().get(0);
        assertEquals(EntityType.ASSIGNMENT, parseAssigment.getType());

        AssignmentImpl assignment = (AssignmentImpl) parseAssigment;
        assertEquals(X, assignment.getVariable().getVarName());

        Factor parsedConstant = ((AssignmentImpl) parseAssigment).getExpression().getFactor();
        assertEquals(EntityType.CONSTANT, parsedConstant.getType());

        ConstantImpl firstConstant = (ConstantImpl) parsedConstant;
        assertEquals(2, firstConstant.getValue());
    }
}
