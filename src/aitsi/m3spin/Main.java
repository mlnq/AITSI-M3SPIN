package aitsi.m3spin;

import aitsi.m3spin.parser.Parser;
import aitsi.m3spin.ui.SimpleReader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        char uiOption = readUiOption();
        switch (uiOption) {
            case '1':
                SimpleReader simpleReader = new SimpleReader();
                String simpleCode = simpleReader.readFile();
                Parser parser = new Parser();
                parser.parseAST();
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
