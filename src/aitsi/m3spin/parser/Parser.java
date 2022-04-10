package aitsi.m3spin.parser;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.commons.impl.AssignmentImpl;
import aitsi.m3spin.commons.impl.ProcedureImpl;
import aitsi.m3spin.commons.impl.VariableImpl;
import aitsi.m3spin.commons.impl.WhileImpl;
import aitsi.m3spin.commons.interfaces.*;
import aitsi.m3spin.parser.exception.MissingCharacterException;
import aitsi.m3spin.parser.exception.MissingCodeEntityException;
import aitsi.m3spin.parser.exception.MissingSimpleKeywordException;
import aitsi.m3spin.parser.exception.SimpleParserException;
import aitsi.m3spin.pkb.AstImpl;
import aitsi.m3spin.pkb.Interfaces.AST;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private AST ast = new AstImpl();
    private CodeScanner codeScanner;

    public Parser(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    public void parse() throws SimpleParserException {
        System.out.println("Parsing SIMPLE code...");
        Procedure rootProc = parseProcedure();
        System.out.println("Parsing completed.");

        ast.setRoot(rootProc);
        //todo zapisać wszystkie sparsowane elementy w PKB za pomocą interfejsu AST
        //todo wypełnić varTable
        //todo wypełnić procTable
    }

    private Procedure parseProcedure() throws SimpleParserException {
        parseKeyword(EntityType.PROCEDURE);
        String procName = parseName();

        parseStartingBrace();

        Procedure procedure = new ProcedureImpl(procName, parseStmtList());

        parseEndingBrace();

        return procedure;
    }

    private void parseStartingBrace() throws MissingCharacterException {
        parseChar('{');
    }

    private void parseEndingBrace() throws MissingCharacterException {
        parseChar('}');
    }

    private void parseChar(char c) throws MissingCharacterException {
        this.codeScanner.skipWhitespaces();
        if (this.codeScanner.hasCurrentChar(c)) {
            this.codeScanner.incrementPosition();
        } else throw new MissingCharacterException(c, this.codeScanner.getCurrentPosition());
    }

    private String parseName() throws SimpleParserException {
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
        return String.valueOf(name);
    }

    private char parseDigit() {
        return 0;//todo analogicznie do parseLetter()
    }

    private char parseLetter() throws SimpleParserException {
        char letter = this.codeScanner.getCurrentLetter();
        this.codeScanner.incrementPosition();
        return letter;
    }

    private void parseKeyword(EntityType keyword) throws MissingCodeEntityException {
        String keywordStr = codeScanner.getCurrentString(keyword.getETName().length());
        if (!keyword.getETName().equals(keywordStr)) {
            throw new MissingSimpleKeywordException(keyword, codeScanner.getCurrentPosition());
        }
        codeScanner.skipWhitespaces();
    }

    private List<Statement> parseStmtList() throws SimpleParserException {
        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(parseStmt());

        while (!this.codeScanner.hasCurrentChar('}')) {
            stmtList.add(parseStmt());
            codeScanner.skipWhitespaces();
        }
        return stmtList;
    }

    private Statement parseStmt() throws SimpleParserException {
        codeScanner.skipWhitespaces();
        String firstWord = parseName();

        codeScanner.skipWhitespaces();
        if (this.codeScanner.hasCurrentChar(EntityType.EQUALS.getETName().charAt(0))) {
            this.codeScanner.incrementPosition();
            return parseAssignmentAfterEquals(firstWord);
        } else if (EntityType.IF.getETName().equals(firstWord)) {
            return parseIf();
        } else {
            return parseWhile();
        }
    }

    private If parseIf() {
        return null;//todo w przyszłych iteracjach
    }

    private While parseWhile() throws SimpleParserException {
//        parseKeyword(EntityType.WHILE);
        String conditionVar = parseName();

        parseStartingBrace();

        List<Statement> stmtList = parseStmtList();

        parseEndingBrace();
        return new WhileImpl(new VariableImpl(conditionVar), stmtList);
    }

    private Assignment parseAssignmentAfterEquals(String leftSideVar) throws MissingCharacterException {
        Expression expr = parseExpression();
        parseChar(';');
        return new AssignmentImpl(new VariableImpl(leftSideVar), expr);
    }

    private Expression parseExpression() {
        return null;
        /*todo zaimplementować parsowanie wyrażeń wg gramatyki Jarząbka:

        expr : expr ‘+’ factor | factor
        factor : var_name | const_value
        var_name : NAME (zaimplementowane jako parseName())
        const_value : INTEGER

        */
    }
}
