package aitsi.m3spin;

import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.pkb.impl.Pkb;
import aitsi.m3spin.query.evaluator.QueryEvaluator;
import aitsi.m3spin.query.QueryPreprocessor;
import aitsi.m3spin.query.QueryResultProjector;
import aitsi.m3spin.spafrontend.extractor.DesignExtractor;
import aitsi.m3spin.spafrontend.parser.Parser;
import aitsi.m3spin.spafrontend.parser.exception.SimpleParserException;
import aitsi.m3spin.ui.PqlDisplayer;
import aitsi.m3spin.ui.PqlReader;
import aitsi.m3spin.ui.SimpleReader;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            setEncoding("UTF-8");

            SimpleReader simpleReader = new SimpleReader();
            PqlReader pqlReader = new PqlReader();
            PqlDisplayer pqlDisplayer = new PqlDisplayer();

            if (args != null && args.length == 1) {
                simpleReader.readFile(args[0]);

                processSimpleCodeAndPreparePkb(simpleReader.getCodeLines());

                System.out.println("Ready");

                while (true) {
                    List<String> pqlLines = pqlReader.readStdin(2);

                    QueryPreprocessor qp = new QueryPreprocessor(pqlLines);
                    qp.parsePql();

                    QueryEvaluator queryEvaluator = new QueryEvaluator(qp.getDeclarationList(), qp.getQueryList());
                    List<TNode> rawResult = queryEvaluator.evaluateQueries();

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

    private static void processSimpleCodeAndPreparePkb(List<String> codeLines) throws SimpleParserException {
        Pkb pkb = new Pkb();
        Parser parser = new Parser(codeLines, pkb);
        parser.parse();

        DesignExtractor designExtractor = new DesignExtractor(pkb);
        designExtractor.extractDesigns();
    }

    private static String formatException(Exception e) {
        return "#" + e.getMessage();
    }
}
