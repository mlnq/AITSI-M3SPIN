package aitsi.m3spin.query.model;

import aitsi.m3spin.query.model.clauses.Pattern;
import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.clauses.SuchThat;
import aitsi.m3spin.query.model.clauses.WithClause;
import aitsi.m3spin.query.model.result.SelectedResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Query {
    private SelectedResult selectedResult;
    private List<SuchThat> suchThatList;
    private List<WithClause> withList;
    private List<Pattern> patternList;

    public List<PqlClause> getAllClauses() {
        return Stream.concat(Stream.concat(suchThatList.stream(), withList.stream()), patternList.stream())
                .collect(Collectors.toList());
    }
}
