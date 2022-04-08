package aitsi.m3spin.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SimpleReader {

    public List<String> readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        List<String> code = new LinkedList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                code.add(scanner.nextLine());
            }

            return code;
        } catch (FileNotFoundException e) {
            throw e;
        }
    }
}
