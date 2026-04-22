import java.util.*;

class Plagiarism {
    private HashMap<String, Set<Integer>> index = new HashMap<>();
    private int n;
    public Plagiarism(int n) {
        this.n = n;
    }
    // Generate n-grams
    private List<String> generateNGrams(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= words.length - n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++) {
                sb.append(words[i + j]).append(" ");
            }
            list.add(sb.toString().trim());
        }
        return list;
    }
    // Add document
    public void addDocument(int docId, String text) {
        List<String> grams = generateNGrams(text);
        for (String gram : grams) {
            index.putIfAbsent(gram, new HashSet<>());
            index.get(gram).add(docId);
        }
    }
    // Check similarity
    public void checkDocument(String text) {
        List<String> grams = generateNGrams(text);
        HashMap<Integer, Integer> matchCount = new HashMap<>();
        for (String gram : grams) {
            if (index.containsKey(gram)) {
                for (int docId : index.get(gram)) {
                    matchCount.put(docId,
                            matchCount.getOrDefault(docId, 0) + 1);
                }
            }
        }
        // Print similarity
        for (int docId : matchCount.keySet()) {
            int matches = matchCount.get(docId);
            double similarity =
                    (matches * 100.0) / grams.size();
            System.out.println(
                    "Doc " + docId +
                            " similarity: " +
                            similarity + "%"
            );
        }
    }
    public static void main(String[] args) {
        Plagiarism ps = new Plagiarism(5);
        ps.addDocument(1,
                "data structures and algorithms are very important");
        ps.addDocument(2,
                "machine learning and artificial intelligence are future");
        ps.checkDocument(
                "data structures and algorithms are very useful");
    }
}