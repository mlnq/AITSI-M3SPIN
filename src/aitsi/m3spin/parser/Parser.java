package aitsi.m3spin.parser;

import aitsi.m3spin.commons.EntityType;
import aitsi.m3spin.commons.ProcedureImpl;
import aitsi.m3spin.commons.Procedure;
import aitsi.m3spin.pkb.Interfaces.AST;

import java.util.*;

public class Parser {
    private Dictionary<String, Object> parserDict = new Hashtable<String, Object>();//todo: zapytać co to
    private AST ast = new AstImpl();
    private CodeScanner codeScanner;

    public Parser(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    public void parse() {
        parseProcedure();
    }

    private void parseProcedure() {
        parseProcedureTag();
        Procedure procedure = new Procedure(parseName());

        ast.setRoot(procedure);

        parseStartingBrace();
        parseStmtList();
        parseEndingBrace();
    }

    private Object parseName() {
        parseLetter();
        while (hasNextNotSpace()) {
            char nextChar = codeScanner.getNextChar();
            if (codeScanner.hasNextLetter()) {
                parseLetter();
            } else if (codeScanner.hasNextDigit()) {
                parseDigit();

            }

            String procedureKeyword = codeScanner.getNextSubstr(EntityType.PROCEDURE.getETName().length());
            nodeList.add(new ProcedureImpl(parts[i + 1]));
        }

        private void parseProcedureTag () throws MissingSimpleKeywordException {
            String procedureKeyword = codeScanner.getNextSubstr(EntityType.PROCEDURE.getETName().length());
            if (!EntityType.PROCEDURE.getETName().equals(procedureKeyword)) {
                throw new MissingSimpleKeywordException(EntityType.PROCEDURE, codeScanner.getCurrentPosition());
            }
        }

        private void parseStmtList () {
            parseStmt();
            if (codeScanner.hasNextStmt()) {
                parseStmt();
            }
        }
    }
