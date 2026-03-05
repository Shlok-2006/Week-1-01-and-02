import java.util.*;

public class UsernameChecker {

    private static HashMap<String, Integer> usernameToUserId = new HashMap<>();
    private static HashMap<String, Integer> attemptFrequency = new HashMap<>();

    public static boolean checkAvailability(String username) {
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);
        return !usernameToUserId.containsKey(username);
    }

    public static List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;
            if (!usernameToUserId.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        String dotVersion = username.replace("_", ".");
        if (!usernameToUserId.containsKey(dotVersion)) {
            suggestions.add(dotVersion);
        }

        return suggestions;
    }

    public static String getMostAttempted() {
        String most = "";
        int max = 0;

        for (String key : attemptFrequency.keySet()) {
            if (attemptFrequency.get(key) > max) {
                max = attemptFrequency.get(key);
                most = key;
            }
        }

        return most + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        usernameToUserId.put("john_doe", 1);
        usernameToUserId.put("admin", 2);
        usernameToUserId.put("user123", 3);

        System.out.println(checkAvailability("john_doe"));
        System.out.println(checkAvailability("jane_smith"));

        System.out.println(suggestAlternatives("john_doe"));

        for (int i = 0; i < 10543; i++) {
            checkAvailability("admin");
        }

        System.out.println(getMostAttempted());
    }
}