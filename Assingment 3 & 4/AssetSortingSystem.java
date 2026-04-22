import java.util.Arrays;
import java.util.Random;

class Asset {
    String name;
    double returnRate;    // percentage, e.g. 12.0 means 12%
    double volatility;    // lower is less volatile

    public Asset(String name, double returnRate, double volatility) {
        this.name = name;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return name + ":" + returnRate + "%";
    }
}

public class AssetSortingSystem {

    private static final int INSERTION_SORT_THRESHOLD = 10;

    // =========================
    // MERGE SORT (Stable, ASC by returnRate)
    // =========================
    public static void mergeSort(Asset[] arr, Asset[] aux, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;
        mergeSort(arr, aux, left, mid);
        mergeSort(arr, aux, mid + 1, right);
        merge(arr, aux, left, mid, right);
    }

    public static void merge(Asset[] arr, Asset[] aux, int left, int mid, int right) {
        System.arraycopy(arr, left, aux, left, right - left + 1);

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            // stable sort: <= to preserve order for ties
            if (aux[i].returnRate <= aux[j].returnRate) {
                arr[k++] = aux[i++];
            } else {
                arr[k++] = aux[j++];
            }
        }

        while (i <= mid) arr[k++] = aux[i++];
        while (j <= right) arr[k++] = aux[j++];
    }

    // =========================
    // QUICK SORT (DESC returnRate + ASC volatility)
    // =========================
    public static void quickSort(Asset[] arr, int low, int high) {
        if (high - low + 1 <= INSERTION_SORT_THRESHOLD) {
            insertionSort(arr, low, high);
            return;
        }

        if (low < high) {
            int pivotIndex = medianOfThree(arr, low, high);
            swap(arr, pivotIndex, high);
            int p = partition(arr, low, high);

            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    // Median-of-three pivot selection
    private static int medianOfThree(Asset[] arr, int low, int high) {
        int mid = (low + high) / 2;
        if (arr[low].returnRate > arr[mid].returnRate) swap(arr, low, mid);
        if (arr[low].returnRate > arr[high].returnRate) swap(arr, low, high);
        if (arr[mid].returnRate > arr[high].returnRate) swap(arr, mid, high);
        return mid;
    }

    private static int partition(Asset[] arr, int low, int high) {
        Asset pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // DESC returnRate first
            if (arr[j].returnRate > pivot.returnRate ||
                    (arr[j].returnRate == pivot.returnRate &&
                            arr[j].volatility < pivot.volatility)) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    // Insertion Sort used for small partitions in QuickSort
    private static void insertionSort(Asset[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Asset key = arr[i];
            int j = i - 1;

            while (j >= low &&
                    (arr[j].returnRate < key.returnRate ||
                            (arr[j].returnRate == key.returnRate &&
                                    arr[j].volatility > key.volatility))) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        Asset[] assets = {
                new Asset("AAPL", 12.0, 0.25),
                new Asset("TSLA", 8.0, 0.35),
                new Asset("GOOG", 15.0, 0.20)
        };

        // Merge Sort (stable, ascending returnRate)
        Asset[] aux = new Asset[assets.length];
        mergeSort(assets, aux, 0, assets.length - 1);
        System.out.println("Merge Sort (ASC returnRate): " + Arrays.toString(assets));

        // Reset original array for quicksort
        assets = new Asset[] {
                new Asset("AAPL", 12.0, 0.25),
                new Asset("TSLA", 8.0, 0.35),
                new Asset("GOOG", 15.0, 0.20)
        };

        // Quick Sort (DESC returnRate + ASC volatility)
        quickSort(assets, 0, assets.length - 1);
        System.out.println("Quick Sort (DESC returnRate + ASC volatility): " + Arrays.toString(assets));
    }
}