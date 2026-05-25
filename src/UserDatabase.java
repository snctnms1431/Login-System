/**
 * UserDatabase.java
 * Simulates a user database using a HashMap.
 * Stores multiple registered users and provides lookup functionality.
 * In a production system, this would connect to a real database.
 *
 * Author: Internship Submission
 * Project: Login System
 */

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {

    // -------------------------------------------------------
    // Data Store
    // -------------------------------------------------------
    // Key: username (String), Value: User object
    private Map<String, User> usersMap;

    // -------------------------------------------------------
    // Constructor — Populate with Dummy Data
    // -------------------------------------------------------

    /**
     * Initializes the database and pre-loads several dummy user accounts.
     * The HashMap maps usernames to their User objects for O(1) lookup.
     */
    public UserDatabase() {
        usersMap = new HashMap<>();
        loadDummyUsers();
    }

    /**
     * Adds all demo accounts into the system.
     * In a real application, users would be loaded from a file or database.
     */
    private void loadDummyUsers() {
        // Format: username, password, displayName, role
        addUser(new User("admin",   "admin@123",  "System Administrator", "ADMIN"));
        addUser(new User("rahul",   "rahul#2024", "Rahul Sharma",         "USER"));
        addUser(new User("priya",   "priya$456",  "Priya Patel",          "USER"));
        addUser(new User("vikram",  "vik@789",    "Vikram Nair",          "USER"));
        addUser(new User("manager", "mgr@pass1",  "Anita Desai",          "MANAGER"));
    }

    /**
     * Registers a single User into the HashMap.
     *
     * @param user The User object to store
     */
    private void addUser(User user) {
        usersMap.put(user.getUsername(), user);
    }

    // -------------------------------------------------------
    // Lookup Methods
    // -------------------------------------------------------

    /**
     * Retrieves a User object by username.
     *
     * @param username The username to search for
     * @return The matching User object, or null if not found
     */
    public User findUser(String username) {
        return usersMap.get(username); // Returns null if username not registered
    }

    /**
     * Checks if a username exists in the database.
     *
     * @param username The username to check
     * @return true if the username is registered, false otherwise
     */
    public boolean userExists(String username) {
        return usersMap.containsKey(username);
    }

    /**
     * Returns the total number of registered users.
     *
     * @return Count of users in the database
     */
    public int getUserCount() {
        return usersMap.size();
    }
}