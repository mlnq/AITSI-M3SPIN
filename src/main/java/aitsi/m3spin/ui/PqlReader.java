package aitsi.m3spin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PqlReader {
    public List<String> readStdin(int numberOfLines, Scanner scanner, boolean isRunManual) {
        List<String> pqlInput = new ArrayList<>();

        for (int i = 0; i < numberOfLines; i++) {
            pqlInput.add(scanner.nextLine());
        }
        if (isRunManual) {
            StringBuilder stringBuilder = new StringBuilder();
            scanner.useDelimiter("\n");
            if (!scanner.hasNext("\n")) {
                stringBuilder.append(scanner.next());
            }
            pqlInput.set(1, String.valueOf(stringBuilder));
        }
        return pqlInput;
    }
}
