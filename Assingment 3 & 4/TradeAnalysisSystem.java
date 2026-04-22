import java.util.Arrays;

class Trade {
    String id;
    int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class TradeAnalysisSystem {

    // =========================
    // MERGE SORT (Ascending - Stable)
    // =========================
    public static void mergeSort(Trade[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    public static void merge(Trade[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Trade[] L = new Trade[n1];
        Trade[] R = new Trade[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            // Stable: <= keeps original order
            if (L[i].volume <= R[j].volume) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // =========================
    // QUICK SORT (Descending)
    // =========================
    public static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Lomuto Partition (DESC)
    public static int partition(Trade[] arr, int low, int high) {
        int pivot = arr[high].volume;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].volume > pivot) { // DESC
                i++;
                Trade temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Trade temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // =========================
    // MERGE TWO SORTED ARRAYS
    // =========================
    public static Trade[] mergeSortedLists(Trade[] a, Trade[] b) {
        Trade[] result = new Trade[a.length + b.length];

        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i].volume <= b[j].volume) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }

        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];

        return result;
    }

    // =========================
    // TOTAL VOLUME
    // =========================
    public static int totalVolume(Trade[] arr) {
        int sum = 0;
        for (Trade t : arr) {
            sum += t.volume;
        }
        return sum;
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        // Clone arrays
        Trade[] mergeArr = trades.clone();
        Trade[] quickArr = trades.clone();

        // Merge Sort (ASC)
        mergeSort(mergeArr, 0, mergeArr.length - 1);
        System.out.println("MergeSort (ASC): " + Arrays.toString(mergeArr));

        // Quick Sort (DESC)
        quickSort(quickArr, 0, quickArr.length - 1);
        System.out.println("QuickSort (DESC): " + Arrays.toString(quickArr));

        // Example: merge two sorted lists (morning + afternoon)
        Trade[] morning = {
                new Trade("t1", 100),
                new Trade("t2", 300)
        };

        Trade[] afternoon = {
                new Trade("t3", 200),
                new Trade("t4", 400)
        };

        mergeSort(morning, 0, morning.length - 1);
        mergeSort(afternoon, 0, afternoon.length - 1);

        Trade[] merged = mergeSortedLists(morning, afternoon);

        System.out.println("Merged Trades: " + Arrays.toString(merged));
        System.out.println("Total Volume: " + totalVolume(merged));
    }
}