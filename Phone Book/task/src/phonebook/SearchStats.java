package phonebook;

public class SearchStats {
    int entryCount;
    int foundCount;
    long duration;

    public String getStats() {
        return String.format("Found %d / %d entries. Time taken: %s", foundCount, entryCount, getTimeTakenString(duration));
    }

    public static String getTimeTakenString(long duration) {
        long minutes = (duration / (1000 * 60)) % 60;
        long seconds = (duration / 1000) % 60;
        long ms = duration % 1000;
        return String.format("%d min. %d sec. %d ms.", minutes, seconds, ms);
    }
}
