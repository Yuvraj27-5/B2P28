import java.util.HashMap;
public class LibrarySystem {
    public static void main(String[] args) {
// ISBN (key) -> Book Title (value)
            HashMap<String, String> library = new HashMap<>();
            library.put("978-0134685991", "Effective Java");
            library.put("978-0596009205", "Head First Java");
            library.put("978-0132350884", "Clean Code");
// O(1) lookup by ISBN
            System.out.println(library.get("978-0134685991")); // Effective Java
// Load Factor = number of entries / number of buckets
// Default: 0.75 (resizes when 75% full)
            System.out.println("Size: " + library.size());
        }
}
