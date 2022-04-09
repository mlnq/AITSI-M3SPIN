package aitsi.m3spin.parser;

import aitsi.m3spin.commons.AssignmentImpl;
import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.commons.ProcedureImpl;
import aitsi.m3spin.commons.VariableImpl;
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
    //private Dictionary<String, Object> parserDict = new Hashtable<String, Object>();//todo: zapytać co to
    private AST ast = new AstImpl();
    private CodeScanner codeScanner;

    public Parser(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    public void parse() throws SimpleParserException {
        System.out.println("Parsing SIMPLE code...");
        parseProcedure();
        System.out.println("Parsing completed.");
    }

    private void parseProcedure() throws SimpleParserException {
        parseProcedureTag();
        String procName = parseName();

        parseStartingBrace();

        Procedure procedure = new ProcedureImpl(procName, parseStmtList());

        parseEndingBrace();

        ast.setRoot(procedure);//todo zapisać wszystkie sparsowane elementy w PKB za pomocą interfejsu AST
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
            char nextChar = codeScanner.getCurrentChar();
            if (Character.isLetter(nextChar)) {
                name.append(parseLetter());
            } else if (Character.isDigit(nextChar)) {
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

    private void parseProcedureTag() throws MissingCodeEntityException {
        String procedureKeyword = codeScanner.getCurrentString(EntityType.PROCEDURE.getETName().length());
        if (!EntityType.PROCEDURE.getETName().equals(procedureKeyword)) {
            throw new MissingSimpleKeywordException(EntityType.PROCEDURE, codeScanner.getCurrentPosition());
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
        if (this.codeScanner.hasCurrentChar('=')) {
            this.codeScanner.incrementPosition();
            return parseAssignmentAfterEquals(firstWord);
        } else {
            return parseWhile();
        }
    }

    private While parseWhile() {
        return null;/*todo zaimplementować parsowanie pętli wg gramatyki Jarząbka:
        while : ‘while’ var_name ‘{‘ stmtLst ‘}’
        var_name : NAME (zaimplementowane jako parseName())
        stmtLst zaimplementowane jako parseStmtList()
        */
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
