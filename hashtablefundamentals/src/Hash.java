import java.util.*;

public class Hash {

    // username -> userId
    private HashMap<String, Integer> users = new HashMap<>();

    // username -> attempt frequency
    private HashMap<String, Integer> attempts = new HashMap<>();

    // Check username availability
    public boolean checkAvailability(String username) {

        // track attempts
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);

        return !users.containsKey(username);
    }

    // Register new user
    public void registerUser(String username, int userId) {
        users.put(username, userId);
    }

    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        // suggestion 1: add numbers
        for (int i = 1; i <= 3; i++) {
            String suggestion = username + i;
            if (!users.containsKey(suggestion))
                suggestions.add(suggestion);
        }

        // suggestion 2: replace underscore
        String alt = username.replace("_", ".");
        if (!users.containsKey(alt))
            suggestions.add(alt);

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {

        String most = "";
        int max = 0;

        for (String key : attempts.keySet()) {
            int count = attempts.get(key);

            if (count > max) {
                max = count;
                most = key;
            }
        }

        return most + " (" + max + " attempts)";
    }

    // Main method for testing
    public static void main(String[] args) {

     Hash checker = new Hash();

        // existing users
        checker.registerUser("john_doe", 101);
        checker.registerUser("admin", 102);

        System.out.println("checkAvailability(\"john_doe\") → "
                + checker.checkAvailability("john_doe"));

        System.out.println("checkAvailability(\"jane_smith\") → "
                + checker.checkAvailability("jane_smith"));

        System.out.println("suggestAlternatives(\"john_doe\") → "
                + checker.suggestAlternatives("john_doe"));

        // simulate attempts
        for (int i = 0; i < 5; i++)
            checker.checkAvailability("admin");

        System.out.println("getMostAttempted() → "
                + checker.getMostAttempted());
    }
}