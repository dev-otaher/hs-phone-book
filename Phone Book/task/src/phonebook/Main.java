package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static String readFileAsString(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startLinearSearch(String directory, String find) {
        System.out.println("Start searching (linear search)...");
        SearchStats stats = new SearchStats();
        long startTime = System.currentTimeMillis();
        for (String entry : find.split("\\r")) {
            stats.entryCount++;
            if (directory.contains(entry.trim())) {
                stats.foundCount++;
            }
        }
        long endTime = System.currentTimeMillis();
        stats.duration = endTime - startTime;
        System.out.println(stats.getStats());
    }

    public static int jumpSearch(String[] array, String value) {
        double step = Math.floor(Math.sqrt(array.length));

        int currentIndex = 0;
        while (currentIndex < array.length) {
            if (array[currentIndex].equals(value)) {
                return currentIndex;
            } else if (array[currentIndex].compareTo(value) > 0) {
                int i = currentIndex - 1;
                while (i > currentIndex - step && i >= 1) {
                    if (array[i].equals(value)) {
                        return i;
                    }
                    i--;
                }
                return -1;
            }
            currentIndex += step;
        }
        int i = array.length - 1;
        while (i > currentIndex - step) {
            if (array[i].equals(value)) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static void startJumpSearch(String directory, String find) {
        System.out.println("Start searching (bubble sort + jump search)...");

        String[] entries = directory.replaceAll("\\d+\\s", "").replaceAll("\\n", "").split("\\r");
        SearchStats stats = new SearchStats();
        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < entries.length; i++) {
//            for (int j = 0; j < entries.length - i - 1; j++) {
//                if (entries[j].compareTo(entries[j + 1]) > 0) {
//                    String temp = entries[j].trim();
//                    entries[j] = entries[j + 1].trim();
//                    entries[j + 1] = temp;
//                }
//            }
//            System.out.println(i);
//        }
        Arrays.sort(entries);
        long sortEndTime = System.currentTimeMillis();

        for (String string : find.split("\\r")) {
            stats.entryCount++;
            if (jumpSearch(entries, string.trim()) > -1) {
                stats.foundCount++;
            }
        }
        long searchEndTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();
        stats.duration = endTime - startTime;
        System.out.println(stats.getStats());

        long sortDuration = sortEndTime - startTime;
        System.out.println("Sorting time: " + SearchStats.getTimeTakenString(sortDuration));

        long searchDuration = searchEndTime - sortEndTime;
        System.out.println("Searching time: " + SearchStats.getTimeTakenString(searchDuration));
    }
        
    public static void main(String[] args) {
        String directory = readFileAsString("D:\\JetBrains Academy\\Phone Book\\directory.txt");
        String find = readFileAsString("D:\\JetBrains Academy\\Phone Book\\find.txt");
        startLinearSearch(directory, find);
        startJumpSearch(directory, find);
    }
}
