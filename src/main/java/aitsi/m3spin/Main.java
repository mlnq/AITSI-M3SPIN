package aitsi.m3spin;

import aitsi.m3spin.commons.exception.CodeScannerException;
import aitsi.m3spin.commons.interfaces.Procedure;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.QueryResultProjector;
import aitsi.m3spin.query.evaluator.QueryEvaluator;
import aitsi.m3spin.query.model.result.actual.QueryResult;
import aitsi.m3spin.query.preprocessor.QueryPreprocessor;
import aitsi.m3spin.spafrontend.extractor.DesignExtractor;
import aitsi.m3spin.spafrontend.parser.Parser;
import aitsi.m3spin.spafrontend.parser.exception.SimpleParserException;
import aitsi.m3spin.ui.PqlDisplayer;
import aitsi.m3spin.ui.PqlReader;
import aitsi.m3spin.ui.SimpleReader;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static boolean isRunManual = false;

    public static void main(String[] args) {
        try {
            setEncoding("UTF-8");

            SimpleReader simpleReader = new SimpleReader();
            PqlReader pqlReader = new PqlReader();
            PqlDisplayer pqlDisplayer = new PqlDisplayer();

            if (args != null && (args.length == 1 || args.length == 2)) {
                isRunManual = args.length == 2;

                simpleReader.readFile(args[0]);

                Pkb pkb = processSimpleCodeAndPreparePkb(simpleReader.getCodeLines());

                System.out.println("Ready");

                Scanner scanner = new Scanner(System.in);

                while (true) {
                    List<String> pqlLines = pqlReader.readStdin(2, scanner, isRunManual);

                    QueryPreprocessor qp = new QueryPreprocessor(pqlLines);
                    qp.parsePql();

                    QueryEvaluator queryEvaluator = new QueryEvaluator(pkb);
                    List<QueryResult> rawResult = queryEvaluator.evaluateQueries(qp.getQueryList());

                    QueryResultProjector queryResultProjector = new QueryResultProjector();
                    String formattedResult = queryResultProjector.formatResult(rawResult);

                    pqlDisplayer.writeStdout(formattedResult);
                }
            } else {
                throw new IllegalArgumentException("SPA received illegal number of arguments: " + args.length +
                        ". Only exactly one argument allowed.");
            }
        } catch (Exception e) {
            System.out.println(formatException(e));
        }
    }

    private static void setEncoding(String encoding) throws NoSuchFieldException, IllegalAccessException {
        System.setProperty("file.encoding", encoding);
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null, null);
    }

    private static Pkb processSimpleCodeAndPreparePkb(List<String> codeLines) throws SimpleParserException, CodeScannerException {
        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        List<Procedure> parsedProcedures = parser.parse();
        parser.fillPkb(parsedProcedures);

        DesignExtractor designExtractor = new DesignExtractor(pkb);
        designExtractor.extractDesigns();
        return pkb;
    }

    private static String formatException(Exception e) {
        if (isRunManual)
            return String.format("#[MESSAGE]:%s%n[TRACE]:%s", e.toString(), Arrays.toString(e.getStackTrace()).replace("),", "),\n"));
        else return String.format("#[MESSAGE]:%s|[TRACE]:%s", e.toString(), Arrays.toString(e.getStackTrace()));
    }
}
