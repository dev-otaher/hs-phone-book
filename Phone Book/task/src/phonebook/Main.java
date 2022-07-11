package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static String readFileAsString(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Start searching...");
        String directory = readFileAsString("D:\\JetBrains Academy\\Phone Book\\directory.txt");
        try {
            Scanner scanner = new Scanner(new File("D:\\JetBrains Academy\\Phone Book\\find.txt"));
            int entryCount = 0, foundCount = 0;
            long startTime = System.currentTimeMillis();
            while (scanner.hasNext()) {
                String fullName = scanner.nextLine();
                entryCount++;
                if (directory.contains(fullName)) {
                    foundCount++;
                }
            }
            long endTime = System.currentTimeMillis();
            long difference = endTime - startTime;
            long minutes = (difference / (1000 * 60)) % 60;
            long seconds = (difference / 1000) % 60;
            long ms = difference % 1000;
            System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.", foundCount, entryCount, minutes, seconds, ms);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
