package aitsi.m3spin.query;

import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.query.model.result.actual.AttrListResult;
import aitsi.m3spin.query.model.result.actual.BooleanResult;
import aitsi.m3spin.query.model.result.actual.QueryResult;
import aitsi.m3spin.query.model.result.actual.TNodeSetResult;
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

        StringBuilder stringBuilder = new StringBuilder();

        int index = 0;
        for(QueryResult queryResult : rawResult)
        {
            if(queryResult.getClass() == BooleanResult.class) {
                stringBuilder.append(((BooleanResult) queryResult).get());
            }
            else if (queryResult.getClass() == TNodeSetResult.class) {
                int counter=0;
                for(TNode currentNode : ((TNodeSetResult) queryResult).getResult())
                {
                    if(counter!=0)
                        stringBuilder.append(" ");

                    stringBuilder.append(((Statement) currentNode).getStmtLine());
                    counter++;
                }
            }
            else if (queryResult.getClass() == AttrListResult.class){

            }
            else {

            }

            if(index < rawResult.size()-1) {
                stringBuilder.append(", ");
            }
            index++;
        }
        return stringBuilder.toString();
    }
}
