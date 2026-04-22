import java.util.*;

public class UseCase13PalindromeCheckerApp {

    // 1️ Two Pointer Approach
    public static boolean twoPointerCheck(String str) {
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // 2️ Stack Approach
    public static boolean stackCheck(String str) {
        Stack<Character> stack = new Stack<>();

        for (char ch : str.toCharArray()) {
            stack.push(ch);
        }

        for (char ch : str.toCharArray()) {
            if (ch != stack.pop()) {
                return false;
            }
        }
        return true;
    }

    // 3️ Recursive Approach
    public static boolean recursiveCheck(String str, int start, int end) {
        if (start >= end)
            return true;

        if (str.charAt(start) != str.charAt(end))
            return false;

        return recursiveCheck(str, start + 1, end - 1);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== UC13: Palindrome Performance Comparison ===");
        System.out.print("Enter a string: ");

        String input = scanner.nextLine();
        String normalized = input.replaceAll("\\s+", "").toLowerCase();

        // Two Pointer Timing
        long startTime = System.nanoTime();
        boolean result1 = twoPointerCheck(normalized);
        long endTime = System.nanoTime();
        long twoPointerTime = endTime - startTime;

        // Stack Timing
        startTime = System.nanoTime();
        boolean result2 = stackCheck(normalized);
        endTime = System.nanoTime();
        long stackTime = endTime - startTime;

        // Recursive Timing
        startTime = System.nanoTime();
        boolean result3 = recursiveCheck(normalized, 0, normalized.length() - 1);
        endTime = System.nanoTime();
        long recursiveTime = endTime - startTime;

        // Display Results
        System.out.println("\n--- Results ---");
        System.out.println("Two Pointer Result : " + result1 +
                " | Time: " + twoPointerTime + " ns");

        System.out.println("Stack Result       : " + result2 +
                " | Time: " + stackTime + " ns");

        System.out.println("Recursive Result   : " + result3 +
                " | Time: " + recursiveTime + " ns");

        scanner.close();
    }
}