package aitsi.m3spin.query.model;

import aitsi.m3spin.query.model.clauses.Pattern;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.result.reference.SelectedResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Query {
    private SelectedResult selectedResult;
    private List<SuchThat> suchThatList = Collections.emptyList();
    private List<WithClause> withList = Collections.emptyList();
    private List<Pattern> patternList = Collections.emptyList();

    public List<PqlClause> getAllClauses() {
        List<PqlClause> allClauses = new ArrayList<>();
        if (suchThatList != null) allClauses.addAll(suchThatList);
        if (withList != null) allClauses.addAll(withList);
        if (patternList != null) allClauses.addAll(patternList);
        return allClauses;
    }
}
