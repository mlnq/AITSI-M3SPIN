package aitsi.m3spin.query;

import aitsi.m3spin.query.model.Synonym;
import aitsi.m3spin.query.model.Query;
import aitsi.m3spin.spafrontend.parser.CodeScanner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class QueryPreprocessor {
    private final List<Synonym> synonymList = new ArrayList<>();
    private final List<Query> queryList = new ArrayList<>();
    private CodeScanner codeScanner;

    public QueryPreprocessor(List<String> code) {
        this.codeScanner = new CodeScanner(code);
    }

    public void parsePql() {
        //todo zadanie #10 - Michał i Natalia
    }
}
