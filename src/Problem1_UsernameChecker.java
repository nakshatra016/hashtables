import java.util.*;

public class Problem1_UsernameChecker {

    // Stores username -> userId
    private HashMap<String, Integer> userDatabase;

    // Stores username -> number of times checked
    private HashMap<String, Integer> attemptFrequency;

    public Problem1_UsernameChecker() {
        userDatabase = new HashMap<>();
        attemptFrequency = new HashMap<>();
    }

    // Add existing user
    public void addUser(String username, int userId) {
        userDatabase.put(username.toLowerCase(), userId);
    }

    // Check availability in O(1)
    public boolean checkAvailability(String username) {
        username = username.toLowerCase();

        // track attempts
        attemptFrequency.put(
                username,
                attemptFrequency.getOrDefault(username, 0) + 1
        );

        return !userDatabase.containsKey(username);
    }

    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        username = username.toLowerCase();

        int number = 1;

        while (suggestions.size() < 3) {
            String suggestion = username + number;

            if (!userDatabase.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
            number++;
        }

        // also suggest dot variation
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    // Return most attempted username
    public String getMostAttempted() {
        String result = null;
        int maxAttempts = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {
            if (entry.getValue() > maxAttempts) {
                maxAttempts = entry.getValue();
                result = entry.getKey();
            }
        }

        if (result == null) {
            return "No attempts yet.";
        }

        return result + " (" + maxAttempts + " attempts)";
    }

    // Main method for testing
    public static void main(String[] args) {

        Problem1_UsernameChecker checker = new Problem1_UsernameChecker();

        // Existing users
        checker.addUser("john_doe", 1);
        checker.addUser("admin", 2);

        System.out.println("Check john_doe: "
                + checker.checkAvailability("john_doe"));

        System.out.println("Check jane_smith: "
                + checker.checkAvailability("jane_smith"));

        System.out.println("Suggestions for john_doe: "
                + checker.suggestAlternatives("john_doe"));

        checker.checkAvailability("admin");
        checker.checkAvailability("admin");
        checker.checkAvailability("admin");

        System.out.println("Most attempted username: "
                + checker.getMostAttempted());
    }
}