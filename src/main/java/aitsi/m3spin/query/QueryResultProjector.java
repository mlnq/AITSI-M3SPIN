package aitsi.m3spin.query;

import aitsi.m3spin.commons.impl.ConstantImpl;
import aitsi.m3spin.commons.impl.ProcedureImpl;
import aitsi.m3spin.commons.impl.StatementListImpl;
import aitsi.m3spin.commons.impl.VariableImpl;
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

        StringBuilder stringBuilder = new StringBuilder();

        int index = 0;
        for (QueryResult queryResult : rawResult) {
            if (queryResult.getClass() == BooleanResult.class) {
                stringBuilder.append(((BooleanResult) queryResult).get());
            } else if (queryResult.getClass() == TNodeSetResult.class) {
                int counter = 0;
                for (TNode currentNode : ((TNodeSetResult) queryResult).getResult()) {
                    if (counter != 0)
                        stringBuilder.append(" ");
                    /*
                    The query result is to be shown as follows:
                         in case of procedures: a procName,
                         in case of statements: an integer stmt#,
                         in case of stmtLst: a stmt# of the first statement in the list,
                         in case of variable: a varName,
                         in case of constant: a value (integer), and
                         in case of prog_line: a program line number (integer)
                     */

                    switch (currentNode.getType()) {
                        case PROCEDURE:
                            stringBuilder.append(((ProcedureImpl) currentNode).getProcName().getValue());
                            break;
                        case STMT_LIST:
                            stringBuilder.append(((StatementListImpl) currentNode).getStatements().get(0).getStmtLine());
                            break;
                        case VARIABLE:
                            stringBuilder.append(((VariableImpl) currentNode).getNameAttr());
                            break;
                        case CONSTANT:
                            stringBuilder.append(((ConstantImpl) currentNode).getValue().getValue());
                            break;
                        case STATEMENT:
                            stringBuilder.append(((Statement) currentNode).getStmtLine());
                            break;
                        default:
                            stringBuilder.append(currentNode);
                            break;
                    }
                    counter++;
                }
            } else {
                stringBuilder.append(queryResult);
            }

            if (index < rawResult.size() - 1) {
                stringBuilder.append(", ");
            }
            index++;
        }
        return stringBuilder.toString();
    }
}
