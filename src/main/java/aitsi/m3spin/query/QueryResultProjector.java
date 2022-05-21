package aitsi.m3spin.query;

import aitsi.m3spin.query.model.result.QueryResult;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class QueryResultProjector {

    /*//todo zadanie #17
    * Odpowiedź składa się z elementów (string) oddzielonych przecinkami (spacje są
    opcjonalne). Sortowanie nie ma znaczenia. W przypadku zapytań zwracających „tuple”, np.
    stmt s, s1;
    Select <s, s1> such that .........
    obiekty wewnątrz < > oddzielane są spacją:
    1 2, 5 8, 13 19
    *
    * Elementy odpowiedzi są unikalne, tzn. bez powtórzeń. - ewentualnie do Evaluatora
    * */
    public String formatResult(List<QueryResult> rawResult) {
        return null;
    }
}
