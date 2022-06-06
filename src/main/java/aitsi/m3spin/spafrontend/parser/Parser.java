package aitsi.m3spin.spafrontend.parser;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.exception.CodeScannerException;
import aitsi.m3spin.commons.exception.MissingCharacterException;
import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.spafrontend.parser.exception.SimpleParserException;
import aitsi.m3spin.spafrontend.parser.exception.UnknownStatementType;
import aitsi.m3spin.spafrontend.parser.exception.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    @Getter
    private final CodeScanner codeScanner;
    private int counter;

    public Parser(List<String> code) {
        this.codeScanner = new CodeScanner(code);
        this.counter = 0;
    }

    public List<Procedure> parse() throws SimpleParserException, CodeScannerException {
        System.out.println("Parsing SIMPLE code...");

        List<Procedure> procedures = new ArrayList<>();
        while (codeScanner.isEndOfFile()) {
            procedures.add(parseProcedure());
            this.codeScanner.skipWhitespaces();
        }

        System.out.println("Parsing completed.");
        return procedures;
    }

    private Procedure parseProcedure() throws SimpleParserException, CodeScannerException {
        codeScanner.parseKeyword(EntityType.PROCEDURE.getETName());
        String procName = parseName();

        parseStartingBrace();

        Procedure procedure = new ProcedureImpl(procName, parseStmtList());

        //Whole endingBrace
        parseChar('}', true);

        return procedure;
    }

    private void parseStartingBrace() throws MissingCharacterException {
        parseChar('{');
    }

    private void parseEndingBrace() throws MissingCharacterException {
        parseChar('}');
    }

    private void parseChar(char c) throws MissingCharacterException {//todo do codeScannera (zadanie ATS-11)
        parseChar(c, true);
    }

    private void parseChar(char c, boolean incFlag) throws MissingCharacterException {//todo do codeScannera (zadanie ATS-11)
        this.codeScanner.skipWhitespaces();
        if (this.codeScanner.hasCurrentChar(c)) {
            if (incFlag) {
                this.codeScanner.incrementPosition();
            }
            codeScanner.skipWhitespaces();
        } else {
            throw new MissingCharacterException(c, this.codeScanner.getCurrentPosition());
        }
    }

    private String parseName() throws CodeScannerException {//todo do codeScannera (zadanie ATS-11)
        StringBuilder name = new StringBuilder(String.valueOf(parseLetter()));
        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isLetter(currentChar)) {
                name.append(parseLetter());
            } else if (Character.isDigit(currentChar)) {
                name.append(parseDigit());
            } else {
                break;
            }
        }
        codeScanner.skipWhitespaces();
        return String.valueOf(name);
    }

    private char parseDigit() throws CodeScannerException {//todo do codeScannera (zadanie ATS-11)
        char digit = this.codeScanner.getCurrentDigit();
        this.codeScanner.incrementPosition();
        return digit;
    }

    private char parseLetter() throws CodeScannerException {//todo do codeScannera (zadanie ATS-11)
        char letter = this.codeScanner.getCurrentLetter();
        this.codeScanner.incrementPosition();
        return letter;
    }

    private StatementList parseStmtList() throws SimpleParserException, CodeScannerException {
        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(parseStmt());

        while (!this.codeScanner.hasCurrentChar('}')) {
            stmtList.add(parseStmt());
            codeScanner.skipWhitespaces();
        }
        return new StatementListImpl(stmtList);
    }

    private Statement parseStmt() throws SimpleParserException, CodeScannerException {
        codeScanner.skipWhitespaces();
        String firstWord = parseName();

        codeScanner.skipWhitespaces();
        Statement st;
        if (this.codeScanner.hasCurrentChar(EntityType.EQUALS.getETName().charAt(0))) {
            this.codeScanner.incrementPosition();
            st = parseAssignmentAfterEquals(firstWord);
        } else if (EntityType.WHILE.getETName().equals(firstWord)) {
            st = parseWhile();
        } else if (EntityType.IF.getETName().equals(firstWord)) {
            st = parseIf();
        } else if (EntityType.CALL.getETName().equals(firstWord)) {
            return parseCall();
        } else throw new UnknownStatementType(codeScanner.getCurrentPosition());
        st.setStmtLine(++counter);
        return st;
    }

    private Call parseCall() throws SimpleParserException {
        parseName();
        parseChar(';');
        return new CallImpl(); //todo po 1 iteracji
    }

    private If parseIf() throws SimpleParserException {
        parseName();
        parseKeyword(EntityType.THEN);
        parseChar('{');
        parseStmtList();
        parseChar('}');
        parseKeyword(EntityType.ELSE);
        parseChar('{');
        parseStmtList();
        parseChar('}');
        return new IfImpl();//todo po 1 iteracji
    }

    private While parseWhile() throws SimpleParserException, CodeScannerException {
        String conditionVar = parseName();

        parseStartingBrace();

        StatementList stmtList = parseStmtList();

        parseEndingBrace();
        return new WhileImpl(new VariableImpl(conditionVar), stmtList);
    }

    private Assignment parseAssignmentAfterEquals(String leftSideVar) throws CodeScannerException {
        codeScanner.skipWhitespaces();
        Expression expr = parseExpression();

        parseChar(';');
        return new AssignmentImpl(new VariableImpl(leftSideVar), expr);
    }

    private Expression parseExpression() throws CodeScannerException {

        codeScanner.skipWhitespaces();
        Factor factor = parseFactor();
        codeScanner.skipWhitespaces();

        if (codeScanner.getCurrentChar() == EntityType.PLUS.getETName().charAt(0)) {
            codeScanner.incrementPosition();
            codeScanner.skipWhitespaces();
            return new ExpressionImpl(factor, parseExpression());
        } else {
            return new ExpressionImpl(factor);
        }
    }

    private Factor parseFactor() throws CodeScannerException {
        codeScanner.skipWhitespaces();
        Factor factor;
        if (Character.isLetter(this.codeScanner.getCurrentChar())) {
            factor = new VariableImpl(parseName());
        } else {
            factor = new ConstantImpl(parseConst());
        }
        return factor;
    }

    private int parseConst() throws CodeScannerException {
        StringBuilder name = new StringBuilder(String.valueOf(parseDigit()));
        while (codeScanner.hasCurrentChar()) {
            char currentChar = codeScanner.getCurrentChar();
            if (Character.isDigit(currentChar)) {
                name.append(parseDigit());
            } else {
                break;
            }
        }
        return Integer.parseInt(String.valueOf(name));
    }
}
