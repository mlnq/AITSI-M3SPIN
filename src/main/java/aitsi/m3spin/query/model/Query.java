package aitsi.m3spin.query.model;

import aitsi.m3spin.query.model.clauses.PqlClause;
import aitsi.m3spin.query.model.references.Synonym;
import aitsi.m3spin.query.model.result.reference.SelectedResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Query {
    private SelectedResult selectedResult;
    @Builder.Default
    private List<PqlClause> clauses = Collections.emptyList();

//    public List<PqlClause> getAllClauses() {
//        List<PqlClause> allClauses = new ArrayList<>();
//        if (suchThatList != null) allClauses.addAll(suchThatList);
//        if (withList != null) allClauses.addAll(withList);
//        if (patternList != null) allClauses.addAll(patternList);
//        return allClauses;
//    }

    /*
        Schemat działania metody:
     * 1. ze wszystkich klauzul wyberz te, które używają synonimu selSyn
     *   2. Wrzuć w zbiór wszystkie inne synonimy, które są używane w tych klauzulach
     *       3. Dla każdego synonimu z tego zbioru odpal punkt 1. przekazując ten zbiór już powiązanych
     * */
    public Set<Synonym> getRelatedSynonyms(Synonym selectedSynonym, Set<Synonym> relatedSynonyms) {
        List<PqlClause> allClauses = this.clauses;

        allClauses.stream()
                .filter(pqlClause -> pqlClause.usesSynonym(selectedSynonym))
                .map((PqlClause clause) -> clause.getOtherUsedSynonym(selectedSynonym))
                .filter(Objects::nonNull)
                .filter(relatedSynonyms::add)
                .forEach(usedSynonym -> relatedSynonyms.addAll(getRelatedSynonyms(usedSynonym, relatedSynonyms)));

        return relatedSynonyms;
    }
}
