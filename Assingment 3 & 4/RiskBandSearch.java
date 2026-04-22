import java.util.Arrays;

public class RiskBandSearch {

    // =========================
    // Linear Search for threshold match
    // =========================
    public static void linearSearch(int[] bands, int target) {
        int comparisons = 0;
        boolean found = false;

        for (int i = 0; i < bands.length; i++) {
            comparisons++;
            if (bands[i] == target) {
                System.out.println("Linear Search: Found " + target + " at index " + i +
                        " (" + comparisons + " comparisons)");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Linear Search: " + target + " not found (" + comparisons + " comparisons)");
        }
    }

    // =========================
    // Binary Search: floor & ceiling
    // =========================
    public static void binaryFloorCeiling(int[] sortedBands, int target) {
        int low = 0, high = sortedBands.length - 1;
        int comparisons = 0;
        int floor = Integer.MIN_VALUE;
        int ceiling = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (sortedBands[mid] == target) {
                floor = ceiling = sortedBands[mid];
                break;
            } else if (sortedBands[mid] < target) {
                floor = sortedBands[mid];  // largest ≤ target so far
                low = mid + 1;
            } else {
                ceiling = sortedBands[mid]; // smallest ≥ target so far
                high = mid - 1;
            }
        }

        System.out.println("Binary Search for target " + target + ":");
        System.out.println("Floor: " + (floor != Integer.MIN_VALUE ? floor : "None"));
        System.out.println("Ceiling: " + (ceiling != Integer.MAX_VALUE ? ceiling : "None"));
        System.out.println("Comparisons: " + comparisons);
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {
        int[] riskBands = {10, 25, 50, 100}; // already sorted
        int threshold = 30;

        // Linear Search (unsorted)
        linearSearch(riskBands, threshold);

        // Binary Search (sorted)
        binaryFloorCeiling(riskBands, threshold);
    }
}