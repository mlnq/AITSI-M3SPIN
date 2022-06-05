package aitsi.m3spin.ui;

import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
public class SimpleReader {
    private final List<String> codeLines = new ArrayList<>();

    public void readFile(@NonNull String fileName) throws FileNotFoundException {
        File file = new File(fileName);

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            codeLines.add(scanner.nextLine());
        }
    }
}
