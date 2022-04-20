package aitsi.m3spin.queryProcessor;

import aitsi.m3spin.parser.CodeScanner;

import java.util.List;

public class QueryProcessor {
    private CodeScanner codeScanner;

    public QueryProcessor(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }
}
