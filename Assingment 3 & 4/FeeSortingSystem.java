import java.util.ArrayList;
import java.util.List;

class Transaction {
    String id;
    double fee;
    String timestamp;

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + ":" + fee + "@" + timestamp;
    }
}

public class FeeSortingSystem {

    // =========================
    // Bubble Sort (by fee)
    // =========================
    public static void bubbleSort(List<Transaction> list) {
        int n = list.size();
        int passes = 0, swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++;

            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    // swap
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swaps++;
                    swapped = true;
                }
            }

            // Early termination
            if (!swapped) break;
        }

        System.out.println("BubbleSort Result: " + list);
        System.out.println("Passes: " + passes + ", Swaps: " + swaps);
    }

    // =========================
    // Insertion Sort (fee + timestamp)
    // =========================
    public static void insertionSort(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            // Sort by fee, then timestamp
            while (j >= 0 &&
                    (list.get(j).fee > key.fee ||
                            (list.get(j).fee == key.fee &&
                                    list.get(j).timestamp.compareTo(key.timestamp) > 0))) {

                list.set(j + 1, list.get(j)); // shift right
                j--;
            }
            list.set(j + 1, key);
        }

        System.out.println("InsertionSort Result: " + list);
    }

    // =========================
    // High-fee Outliers (> 50)
    // =========================
    public static void findOutliers(List<Transaction> list) {
        System.out.print("High-fee outliers: ");
        boolean found = false;

        for (Transaction t : list) {
            if (t.fee > 50) {
                System.out.print(t + " ");
                found = true;
            }
        }

        if (!found) {
            System.out.print("none");
        }
        System.out.println();
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        // Sample Input
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        // Clone lists for separate sorting
        List<Transaction> bubbleList = new ArrayList<>(transactions);
        List<Transaction> insertionList = new ArrayList<>(transactions);

        // Bubble Sort (small batch)
        bubbleSort(bubbleList);

        // Insertion Sort (medium batch)
        insertionSort(insertionList);

        // Outlier detection
        findOutliers(transactions);
    }
}