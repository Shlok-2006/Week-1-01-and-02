import java.util.*;

class TrieNode {
    HashMap<Character, TrieNode> children = new HashMap<>();
    boolean isEnd;
    int frequency;
}

public class AutocompleteSystem {

    static TrieNode root = new TrieNode();
    static HashMap<String, Integer> queryFrequency = new HashMap<>();

    public static void insert(String query) {

        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);

        TrieNode node = root;

        for (char c : query.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }

        node.isEnd = true;
        node.frequency = queryFrequency.get(query);
    }

    static void dfs(TrieNode node, String prefix, List<String> result) {

        if (node.isEnd) {
            result.add(prefix);
        }

        for (char c : node.children.keySet()) {
            dfs(node.children.get(c), prefix + c, result);
        }
    }

    public static List<String> search(String prefix) {

        TrieNode node = root;

        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>();
            }
            node = node.children.get(c);
        }

        List<String> results = new ArrayList<>();
        dfs(node, prefix, results);

        results.sort((a, b) -> queryFrequency.get(b) - queryFrequency.get(a));

        return results.size() > 10 ? results.subList(0, 10) : results;
    }

    public static void main(String[] args) {

        insert("java tutorial");
        insert("javascript");
        insert("java download");
        insert("java tutorial");
        insert("java 21 features");

        System.out.println(search("jav"));
    }
}