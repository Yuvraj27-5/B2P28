//Problem 1: Social Media Username Availability Checker
//Scenario: You're building a registration system for a social media platform with 10 million users.
//Users frequently check if usernames are available before registering.
//Problem Statement: Design a system to check username availability in real-time. The system
//should:
//● Check if a username exists in O(1) time
//● Handle 1000 concurrent username checks per second
//● Suggest similar available usernames if the requested one is taken
//● Track popularity of attempted usernames
//Concepts Covered:
//● Hash table basics (key-value mapping)
//● O(1) lookup performance
//● Collision handling0
//● Frequency counting

import java.util.*;

public class SocialMedia {

    // Stores registered usernames → O(1) lookup
    private HashSet<String> usernames;

    // Tracks popularity (how many times username was searched)
    private HashMap<String, Integer> popularity;

    public SocialMedia() {
        usernames = new HashSet<>();
        popularity = new HashMap<>();
    }

    // Register username
    public boolean registerUsername(String name) {
        if (usernames.contains(name)) {
            return false; // already exists
        }
        usernames.add(name);
        return true;
    }

    // Check availability in O(1)
    public boolean isAvailable(String name) {
        popularity.put(name, popularity.getOrDefault(name, 0) + 1);
        return !usernames.contains(name);
    }

    // Suggest similar usernames
    public List<String> suggestUsernames(String name) {
        List<String> suggestions = new ArrayList<>();

        int suffix = 1;

        while (suggestions.size() < 5) {
            String suggestion = name + suffix;
            if (!usernames.contains(suggestion)) {
                suggestions.add(suggestion);
            }
            suffix++;
        }

        return suggestions;
    }

    // Get popularity count
    public int getPopularity(String name) {
        return popularity.getOrDefault(name, 0);
    }

    // Display all usernames
    public void displayUsernames() {
        System.out.println(usernames);
    }

    public static void main(String[] args) {

        SocialMedia sm = new SocialMedia();

        // Register usernames
        sm.registerUsername("john");
        sm.registerUsername("alex");
        sm.registerUsername("john1");

        // Check availability
        String check = "john";

        if (sm.isAvailable(check)) {
            System.out.println(check + " is available");
        } else {
            System.out.println(check + " is taken");

            System.out.println("Suggestions:");
            List<String> suggestions = sm.suggestUsernames(check);
            for (String s : suggestions) {
                System.out.println(s);
            }
        }

        // Popularity count
        System.out.println("Popularity of john: " + sm.getPopularity("john"));
    }
}
