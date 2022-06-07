package aitsi.m3spin.query;

import aitsi.m3spin.commons.impl.*;
import aitsi.m3spin.commons.interfaces.Statement;
import aitsi.m3spin.commons.interfaces.TNode;
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

        StringBuilder formattedOutput = new StringBuilder();

        for (int i = 0; i < rawResult.size(); i++) {
            QueryResult queryResult = rawResult.get(i);

            if (queryResult.getClass() == BooleanResult.class) {
                formattedOutput.append(((BooleanResult) queryResult).get());
            } else if (queryResult.getClass() == TNodeSetResult.class) {
                int index = 0;
                for (TNode currentNode : ((TNodeSetResult) queryResult).getResult()) {
                    if (index != 0)
                        formattedOutput.append(", ");

                    switch (currentNode.getType()) {
                        case PROCEDURE:
                            formattedOutput.append(((ProcedureImpl) currentNode).getProcName());
                            break;
                        case STMT_LIST:
                            formattedOutput.append(((StatementListImpl) currentNode).getStatements().get(0).getProgLine());
                            break;
                        case VARIABLE:
                            formattedOutput.append(((VariableImpl) currentNode).getVarName());
                            break;
                        case CONSTANT:
                            formattedOutput.append(((ConstantImpl) currentNode).getValue());
                            break;
                        case STATEMENT:
                        case ASSIGNMENT:
                        case IF:
                        case WHILE:
                        case CALL:
                            formattedOutput.append(((Statement) currentNode).getProgLine());
                            break;
                        default:
                            formattedOutput.append(currentNode);
                            break;
                    }
                    index++;
                }

                if(((TNodeSetResult) queryResult).getResult().size() == 0)
                    formattedOutput.append("none");

            } else {
                formattedOutput.append(queryResult);
            }

            if (i < rawResult.size() - 1) {
                formattedOutput.append(", ");
            }
        }

        if(rawResult.size() == 0)
            formattedOutput.append("none");

        return formattedOutput.toString();
    }
}
