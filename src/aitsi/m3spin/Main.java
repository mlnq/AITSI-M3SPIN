package aitsi.m3spin;

import aitsi.m3spin.commons.impl.TNodeImpl;
import aitsi.m3spin.commons.interfaces.TNode;
import aitsi.m3spin.parser.Parser;
import aitsi.m3spin.parser.exception.SimpleParserException;
import aitsi.m3spin.ui.SimpleReader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SimpleParserException {//todo obsłużyć ten wyjątek

        System.out.println("Waiting for input");
        char uiOption = readUiOption();
        switch (uiOption) {
            case '1':
                SimpleReader simpleReader = new SimpleReader();
                List<String> codeLines = null;
                try {
                    codeLines = simpleReader.readFile("procedura.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Parser parser = new Parser(codeLines);
                parser.parse();
                break;
            case '2':
                break;
            case '3':
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + uiOption);
        }

    }

    static char readUiOption() {
        Scanner s = new Scanner(System.in);
        return s.next().charAt(0);
    }
}
