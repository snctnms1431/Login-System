/**
 * User.java
 * Represents a single registered user in the Login System.
 * Stores the username, password, and the user's display name.
 * Demonstrates encapsulation with private fields and public getters.
 *
 * Author: Internship Submission
 * Project: Login System
 */

public class User {

    // -------------------------------------------------------
    // Instance Variables (Private — Encapsulation)
    // -------------------------------------------------------
    private String username;     // Unique login identifier
    private String password;     // User's password (stored as plain text for demo)
    private String displayName;  // Friendly name shown after login
    private String role;         // User role, e.g., "ADMIN" or "USER"

    // -------------------------------------------------------
    // Constructor
    // -------------------------------------------------------

    /**
     * Creates a new User with all required fields.
     *
     * @param username    The unique login username
     * @param password    The user's password
     * @param displayName The full name shown after successful login
     * @param role        The role assigned to this user
     */
    public User(String username, String password, String displayName, String role) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
    }

    // -------------------------------------------------------
    // Getters
    // -------------------------------------------------------

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRole() {
        return role;
    }

    // -------------------------------------------------------
    // Utility Method
    // -------------------------------------------------------

    /**
     * Checks if the provided password matches this user's stored password.
     * Uses equals() for proper String comparison (not ==).
     *
     * @param inputPassword Password entered by the user
     * @return true if the password is correct, false otherwise
     */
    public boolean isPasswordCorrect(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}