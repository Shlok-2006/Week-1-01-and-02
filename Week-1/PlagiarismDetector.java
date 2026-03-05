import java.util.*;

public class PlagiarismDetector {

    static HashMap<String, Set<String>> ngramIndex = new HashMap<>();
    static int N = 5;

    public static List<String> generateNGrams(String text) {

        String[] words = text.split(" ");
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

    public static void addDocument(String docId, String text) {

        List<String> ngrams = generateNGrams(text);

        for (String n : ngrams) {
            ngramIndex.putIfAbsent(n, new HashSet<>());
            ngramIndex.get(n).add(docId);
        }
    }

    public static void analyzeDocument(String docId, String text) {

        List<String> ngrams = generateNGrams(text);
        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String n : ngrams) {

            if (ngramIndex.containsKey(n)) {

                for (String d : ngramIndex.get(n)) {
                    matchCount.put(d, matchCount.getOrDefault(d, 0) + 1);
                }
            }
        }

        for (String d : matchCount.keySet()) {

            int matches = matchCount.get(d);
            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Similarity with " + d + ": " + similarity + "%");
        }
    }

    public static void main(String[] args) {

        addDocument("essay_089", "this is a simple document used for plagiarism detection system testing");

        addDocument("essay_092", "plagiarism detection system works by comparing sequences of words in documents");

        analyzeDocument("essay_123", "this is a simple document used for plagiarism detection system");
    }
}