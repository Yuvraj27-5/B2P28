import java.util.Arrays;

public class TransactionSearch {

    // =========================
    // Linear Search: first & last occurrence
    // =========================
    public static void linearSearch(String[] logs, String target) {
        int firstIndex = -1;
        int lastIndex = -1;
        int comparisons = 0;

        for (int i = 0; i < logs.length; i++) {
            comparisons++;
            if (logs[i].equals(target)) {
                if (firstIndex == -1) firstIndex = i;
                lastIndex = i;
            }
        }

        System.out.println("Linear Search for " + target + ":");
        System.out.println("First index: " + firstIndex);
        System.out.println("Last index: " + lastIndex);
        System.out.println("Total comparisons: " + comparisons + "\n");
    }

    // =========================
    // Binary Search (returns first occurrence)
    // =========================
    public static int binarySearchFirst(String[] logs, String target) {
        int low = 0, high = logs.length - 1;
        int firstIndex = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            int cmp = logs[mid].compareTo(target);

            if (cmp == 0) {
                firstIndex = mid;
                high = mid - 1; // search left for first occurrence
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Binary Search (first occurrence) for " + target + ":");
        System.out.println("Index: " + firstIndex + ", Comparisons: " + comparisons);
        return firstIndex;
    }

    // =========================
    // Binary Search (returns last occurrence)
    // =========================
    public static int binarySearchLast(String[] logs, String target) {
        int low = 0, high = logs.length - 1;
        int lastIndex = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            int cmp = logs[mid].compareTo(target);

            if (cmp == 0) {
                lastIndex = mid;
                low = mid + 1; // search right for last occurrence
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Binary Search (last occurrence) for " + target + ":");
        System.out.println("Index: " + lastIndex + ", Comparisons: " + comparisons + "\n");
        return lastIndex;
    }

    // =========================
    // Count total occurrences (first & last index)
    // =========================
    public static int countOccurrences(String[] logs, String target) {
        int first = binarySearchFirst(logs, target);
        int last = binarySearchLast(logs, target);
        if (first == -1) return 0;
        return last - first + 1;
    }

    // =========================
    // MAIN METHOD (Demo)
    // =========================
    public static void main(String[] args) {
        String[] logs = {"accB", "accA", "accB", "accC"};

        // Linear search
        linearSearch(logs, "accB");

        // Binary search requires sorted array
        Arrays.sort(logs);
        System.out.println("Sorted logs: " + Arrays.toString(logs));

        int count = countOccurrences(logs, "accB");
        System.out.println("Binary Search Count for accB: " + count);
    }
}