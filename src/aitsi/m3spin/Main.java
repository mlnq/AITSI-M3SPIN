package aitsi.m3spin;

import aitsi.m3spin.parser.Parser;
import aitsi.m3spin.parser.exception.SimpleParserException;
import aitsi.m3spin.ui.SimpleReader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SimpleParserException {//todo obsłużyć ten wyjątek
        char uiOption = readUiOption();
        switch (uiOption) {
            case '1':
                SimpleReader simpleReader = new SimpleReader();
                List<String> code = null;
                try {
                    code = simpleReader.readFile("procedura.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Parser parser = new Parser(code);
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
