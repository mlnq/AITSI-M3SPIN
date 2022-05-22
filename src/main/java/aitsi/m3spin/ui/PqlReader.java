package aitsi.m3spin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PqlReader {
    public List<String> readStdin(int numberOfLines) {
        List<String> pqlInput = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < numberOfLines; i++) {
            pqlInput.add(scanner.nextLine());
        }
        return pqlInput;
    }
}