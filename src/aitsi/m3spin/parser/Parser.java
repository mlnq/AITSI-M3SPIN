package aitsi.m3spin.parser;

import aitsi.m3spin.pkb.Interfaces.AST;

public class Parser {
    private String rawCode;
    private AST ast = new AstImpl();

    public Parser(String rawCode) {
        this.rawCode = rawCode;
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

    private void parseStmtList() {
        while (rawCode)
    }
}
