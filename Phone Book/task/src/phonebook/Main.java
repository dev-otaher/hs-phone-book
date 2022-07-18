package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    static String[] data;
    static String[] toBeFound;

    public static String readFileAsString(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startLinearSearch() {
        System.out.println("Start searching (linear search)...");
        String[] dataClone = data.clone();
        SearchStats stats = new SearchStats();
        long startTime = System.currentTimeMillis();
        for (String string : toBeFound) {
            stats.entryCount++;
            for (String entry : dataClone) {
                if (entry.equals(string)) {
                    stats.foundCount++;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.printf("Found %d / %d. Time taken: %s\n\n", stats.foundCount, stats.entryCount, SearchStats.formatTimeTaken(duration));
    }

    public static long calcDuration(long startTime, long endTime) {
        return endTime - startTime;
    }

    public static void bubbleSort(String[] array) {
//        for (int i = 0; i < array.length - 1; i++) {
//            for (int j = 0; j < array.length - i - 1; j++) {
//                if (array[j].compareTo(array[j + 1]) > 0) {
//                    swap(array, j, j + 1);
//                }
//            }
//        }
        Arrays.sort(array);
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    public static long startBubbleSort(String[] array) {
        long startTime = System.currentTimeMillis();
        bubbleSort(array);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static SearchStats startJumpSearch(String[] array, String[] toBeFound) {
        SearchStats stats = new SearchStats();
        stats.entryCount = toBeFound.length;
        long startTime = System.currentTimeMillis();
        for (String string : toBeFound) {
            if (jumpSearch(array, string) > -1) {
                stats.foundCount++;
            }
        }
        long endTime = System.currentTimeMillis();
        stats.duration = calcDuration(startTime, endTime);
        return stats;
    }

    public static void startBubbleSortJumpSearch() {
        System.out.println("Start searching (bubble sort + jump search)...");
        String[] dataClone = data.clone();
        long sortDuration = startBubbleSort(dataClone);
        SearchStats searchStats = startJumpSearch(dataClone, toBeFound);
        String totalTime = SearchStats.formatTimeTaken(sortDuration + searchStats.duration);
        System.out.printf("Found %d / %d entries. Time taken: %s\n", searchStats.foundCount, searchStats.entryCount, totalTime);
        System.out.println("Sorting time: " + SearchStats.formatTimeTaken(sortDuration));
        System.out.println("Searching time: " + SearchStats.formatTimeTaken(searchStats.duration) + "\n");
    }

    public static void swap(String[] array, int index1, int index2) {
        String temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static int partition(String[] array, int low, int high) {
        String pivot = array[low];
        int i = high + 1, j = high;

        while (j >= low + 1) {
            if (array[j].compareTo(pivot) > 0) {
                i--;
                swap(array, i, j);
            }
            j--;
        }
        swap(array, i - 1, low);
        return i - 1;
    }

    public static void quickSort(String[] array, int low, int high) {
        if (low < high) {
            int q = partition(array, low, high);
            quickSort(array, low, q - 1);
            quickSort(array, q + 1, high);
        }
    }

    public static int binarySearch(String[] array, String value) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int middleIndex = (left + right) / 2;
            if (value.equals(array[middleIndex])) {
                return middleIndex;
            } else if (value.compareTo(array[middleIndex]) < 0) {
                right = middleIndex - 1;
            } else {
                left = middleIndex + 1;
            }
        }
        return -1;
    }

    public static long startQuicksort(String[] data) {
        long startTime = System.currentTimeMillis();
        quickSort(data, 0, data.length - 1);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static SearchStats startBinarySearch(String[] data, String[] toBeFound) {
        SearchStats stats = new SearchStats();
        stats.entryCount = toBeFound.length;
        long startTime = System.currentTimeMillis();
        for (String string : toBeFound) {
            if (binarySearch(data, string) != -1) {
                stats.foundCount++;
            }
        }
        long endTime = System.currentTimeMillis();
        stats.duration = calcDuration(startTime, endTime);
        return stats;
    }

    public static void startQuicksortBinarySearch() {
        System.out.println("Start searching (quick sort + binary search)...");
        String[] dataClone = data.clone();
        long sortDuration = startQuicksort(dataClone);
        SearchStats searchStats = startBinarySearch(dataClone, toBeFound);
        String totalTime = SearchStats.formatTimeTaken(sortDuration + searchStats.duration);
        System.out.printf("Found %d / %d entries. Time taken: %s\n", searchStats.foundCount, searchStats.entryCount, totalTime);
        System.out.println("Sorting time: " + SearchStats.formatTimeTaken(sortDuration));
        System.out.println("Searching time: " + SearchStats.formatTimeTaken(searchStats.duration) + "\n");
    }

    public static Set<String> createHashTable(String[] data) {
        Set<String> set = new HashSet<>();
        for (String string : data) {
            set.add(string);
        }
        return set;
    }

    public static SearchStats searchHashTable(Set<String> set, String[] toBeFound) {
        SearchStats stats = new SearchStats();
        stats.entryCount = toBeFound.length;
        long startTime = System.currentTimeMillis();
        for (String string : toBeFound) {
            if (set.contains(string)) {
                stats.foundCount++;
            }
        }
        long endTime = System.currentTimeMillis();
        stats.duration = calcDuration(startTime, endTime);
        return stats;
    }

    public static void startHashSearch() {
        System.out.println("Start searching (hash table)...");
        long creationStartTime = System.currentTimeMillis();
        Set<String> set = createHashTable(data);
        long creationEndTime = System.currentTimeMillis();
        long creationDuration = creationEndTime - creationStartTime;
        SearchStats searchStats = searchHashTable(set, toBeFound);
        String totalTime = SearchStats.formatTimeTaken(creationDuration + searchStats.duration);
        System.out.printf("Found %d / %d entries. Time taken: %s\n", searchStats.foundCount, searchStats.entryCount, totalTime);
        System.out.println("Creating time: " + SearchStats.formatTimeTaken(creationDuration));
        System.out.println("Searching time: " + SearchStats.formatTimeTaken(searchStats.duration));
    }

    public static void main(String[] args) {
        String directoryFilename = "D:\\JetBrains Academy\\Phone Book\\directory.txt";
        String toBeFoundFilename = "D:\\JetBrains Academy\\Phone Book\\find.txt";
        data = readFileAsString(directoryFilename).replaceAll("\\d+\\s", "").split("\\r\\n");
        toBeFound = readFileAsString(toBeFoundFilename).split("\\r\\n");
        startLinearSearch();
        startBubbleSortJumpSearch();
        startQuicksortBinarySearch();
        startHashSearch();
    }
}
