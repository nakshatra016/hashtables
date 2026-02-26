import java.util.*;

public class Problem4_PlagiarismDetector {

    private Map<String, Set<String>> ngramIndex = new HashMap<>();
    private int N = 5; // 5-gram

    private List<String> generateNGrams(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(" ");
            }
            ngrams.add(sb.toString().trim());
        }
        return ngrams;
    }

    public void addDocument(String docId, String text) {
        List<String> ngrams = generateNGrams(text);
        for (String ngram : ngrams) {
            ngramIndex.putIfAbsent(ngram, new HashSet<>());
            ngramIndex.get(ngram).add(docId);
        }
    }

    public void analyzeDocument(String docId, String text) {
        List<String> ngrams = generateNGrams(text);
        Map<String, Integer> matchCount = new HashMap<>();

        for (String ngram : ngrams) {
            if (ngramIndex.containsKey(ngram)) {
                for (String otherDoc : ngramIndex.get(ngram)) {
                    matchCount.put(otherDoc,
                            matchCount.getOrDefault(otherDoc, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {
            double similarity = (entry.getValue() * 100.0) / ngrams.size();
            System.out.println("Matched with " + entry.getKey()
                    + " → Similarity: " + String.format("%.2f", similarity) + "%");
        }
    }

    public static void main(String[] args) {
        Problem4_PlagiarismDetector detector = new Problem4_PlagiarismDetector();

        detector.addDocument("doc1", "java is a programming language used widely in software development");
        detector.addDocument("doc2", "java is a programming language used in many applications");

        detector.analyzeDocument("doc3",
                "java is a programming language used widely in many systems");
    }
}