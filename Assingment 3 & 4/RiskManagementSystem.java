import java.util.Arrays;

class Client {
    String name;
    int riskScore;
    double accountBalance;

    public Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return name + ":" + riskScore;
    }
}

public class RiskManagementSystem {

    // =========================
    // Bubble Sort (Ascending Risk)
    // =========================
    public static void bubbleSort(Client[] arr) {
        int n = arr.length;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {

                    // swap
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swaps++;
                    swapped = true;

                    // visualize swap
                    System.out.println("Swap: " + Arrays.toString(arr));
                }
            }

            if (!swapped) break; // early stop
        }

        System.out.println("\nBubble Sorted (ASC): " + Arrays.toString(arr));
        System.out.println("Total swaps: " + swaps);
    }

    // =========================
    // Insertion Sort (DESC Risk + Balance)
    // =========================
    public static void insertionSort(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;

            // Sort by risk DESC, then balance DESC
            while (j >= 0 &&
                    (arr[j].riskScore < key.riskScore ||
                            (arr[j].riskScore == key.riskScore &&
                                    arr[j].accountBalance < key.accountBalance))) {

                arr[j + 1] = arr[j]; // shift
                j--;
            }

            arr[j + 1] = key;
        }

        System.out.println("\nInsertion Sorted (DESC Risk + Balance): "
                + Arrays.toString(arr));
    }

    // =========================
    // Top N Highest Risk Clients
    // =========================
    public static void printTopRisks(Client[] arr, int topN) {
        System.out.print("\nTop " + topN + " High-Risk Clients: ");

        for (int i = 0; i < Math.min(topN, arr.length); i++) {
            System.out.print(arr[i].name + "(" + arr[i].riskScore + ") ");
        }
        System.out.println();
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        Client[] clients = {
                new Client("clientC", 80, 5000),
                new Client("clientA", 20, 2000),
                new Client("clientB", 50, 3000)
        };

        // Clone arrays for separate operations
        Client[] bubbleArr = clients.clone();
        Client[] insertionArr = clients.clone();

        // Bubble Sort (Ascending)
        bubbleSort(bubbleArr);

        // Insertion Sort (Descending)
        insertionSort(insertionArr);

        // Top risks (after descending sort)
        printTopRisks(insertionArr, 10);
    }
}