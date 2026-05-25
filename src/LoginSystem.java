/**
 * LoginSystem.java
 * Core class that manages the login flow for the Login System project.
 * Handles user interaction, authentication logic, attempt tracking,
 * and post-login dashboard. Uses a menu-driven approach.
 *
 * Author: Internship Submission
 * Project: Login System
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginSystem {

    // -------------------------------------------------------
    // Constants
    // -------------------------------------------------------
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final String BORDER =
            "  ================================================================";

    // -------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------
    private UserDatabase database;   // The in-memory user database
    private Scanner scanner;          // For reading user input
    private boolean isSystemLocked;  // Becomes true after too many failed attempts
    private int failedAttempts;       // Counter for consecutive failed logins

    // -------------------------------------------------------
    // Constructor
    // -------------------------------------------------------

    /**
     * Sets up the login system with a fresh database and initializes state.
     */
    public LoginSystem() {
        this.database = new UserDatabase();
        this.scanner = new Scanner(System.in);
        this.isSystemLocked = false;
        this.failedAttempts = 0;
    }

    // -------------------------------------------------------
    // Application Start
    // -------------------------------------------------------

    /**
     * Launches the login system application.
     * Shows the opening screen and starts the main loop.
     */
    public void launch() {
        displaySplashScreen();
        runApplicationLoop();
        displayExitScreen();
        scanner.close();
    }

    /**
     * Displays the splash/welcome screen when the program starts.
     */
    private void displaySplashScreen() {
        System.out.println();
        System.out.println(BORDER);
        System.out.println("  ||                                                            ||");
        System.out.println("  ||          JAVA SECURE LOGIN SYSTEM  v1.0                   ||");
        System.out.println("  ||          Internship Task Submission                        ||");
        System.out.println("  ||                                                            ||");
        System.out.println(BORDER);
        System.out.printf("  Registered Users in Database: %d%n", database.getUserCount());
        System.out.println();
    }

    // -------------------------------------------------------
    // Main Application Loop
    // -------------------------------------------------------

    /**
     * The primary loop that presents the start menu.
     * Keeps running until the user selects Exit (option 2).
     */
    private void runApplicationLoop() {
        int choice = -1;

        do {
            printStartMenu();
            choice = readIntInput();
            System.out.println();

            switch (choice) {
                case 1:
                    handleLoginAttempt();
                    break;
                case 2:
                    System.out.println("  Closing the system. Goodbye!");
                    break;
                default:
                    System.out.println("  [!] Invalid option. Please choose 1 or 2.\n");
            }

        } while (choice != 2);
    }

    /**
     * Prints the initial menu options before login.
     */
    private void printStartMenu() {
        System.out.println();
        System.out.println(BORDER);
        System.out.println("  ||                    START MENU                             ||");
        System.out.println(BORDER);
        System.out.println("  ||  [1]  Login to System                                     ||");
        System.out.println("  ||  [2]  Exit                                                ||");
        System.out.println(BORDER);
        System.out.print("  Enter your choice: ");
    }

    // -------------------------------------------------------
    // Login Logic
    // -------------------------------------------------------

    /**
     * Manages a full login attempt session.
     * Locks the system after MAX_LOGIN_ATTEMPTS consecutive failures.
     * Resets failure count on successful login.
     */
    private void handleLoginAttempt() {

        // Block access if the system was already locked
        if (isSystemLocked) {
            displayLockedMessage();
            return;
        }

        System.out.println(BORDER);
        System.out.println("  USER AUTHENTICATION");
        System.out.println(BORDER);

        // -- Read username --
        System.out.print("  Enter Username : ");
        String username = scanner.nextLine().trim();

        // -- Read password (with mask simulation) --
        String password = readPassword();

        System.out.println();

        // -- Validate credentials --
        boolean loginSuccess = validateCredentials(username, password);

        if (loginSuccess) {
            // Reset failure counter on success
            failedAttempts = 0;
            User loggedInUser = database.findUser(username);
            showDashboard(loggedInUser);
        } else {
            failedAttempts++;
            int attemptsLeft = MAX_LOGIN_ATTEMPTS - failedAttempts;

            System.out.println("  [FAILED] Invalid username or password.");

            if (attemptsLeft > 0) {
                System.out.println("  Warning: " + attemptsLeft
                        + " attempt(s) remaining before system lockout.");
            } else {
                // Max attempts reached — lock the system
                isSystemLocked = true;
                displayLockedMessage();
            }
        }
    }

    /**
     * Checks credentials against the database using the HashMap.
     *
     * @param username Entered username
     * @param password Entered password
     * @return true if username exists and password matches, false otherwise
     */
    private boolean validateCredentials(String username, String password) {

        // Step 1: Check if username exists in the HashMap
        if (!database.userExists(username)) {
            return false; // Unknown user
        }

        // Step 2: Retrieve the User object and check the password
        User user = database.findUser(username);
        return user.isPasswordCorrect(password);
    }

    /**
     * Simulates password masking by asking the user to enter a password.
     * In a real terminal, java.io.Console can hide input; Scanner cannot.
     * Here, we print stars (***) to simulate the concept visually.
     *
     * @return The password string entered by the user
     */
    private String readPassword() {
        System.out.print("  Enter Password : ");

        // Try to use Console for real password hiding (works in terminal, not IDEs)
        java.io.Console console = System.console();

        if (console != null) {
            // Real password masking — characters won't appear on screen
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        } else {
            // Fallback for IDEs (like IntelliJ/Eclipse) where Console is null
            String password = scanner.nextLine();
            // Simulate visual masking feedback
            System.out.println("  Password Input  : " + "*".repeat(password.length()));
            return password;
        }
    }

    // -------------------------------------------------------
    // Post-Login Dashboard
    // -------------------------------------------------------

    /**
     * Displays the user's dashboard after a successful login.
     * Shows role-specific options using a submenu loop.
     *
     * @param user The User who just logged in
     */
    private void showDashboard(User user) {
        displayLoginSuccessMessage(user);
        int choice = -1;

        do {
            printDashboardMenu(user);
            choice = readIntInput();
            System.out.println();

            switch (choice) {
                case 1:
                    showAccountInfo(user);
                    break;
                case 2:
                    // Feature placeholder — can be extended later
                    System.out.println("  [Notifications] No new notifications at this time.\n");
                    break;
                case 3:
                    System.out.println("  You have been logged out. Session closed.\n");
                    break;
                default:
                    System.out.println("  [!] Invalid option. Please choose 1 to 3.\n");
            }

        } while (choice != 3);
    }

    /**
     * Prints the dashboard menu shown after successful login.
     *
     * @param user The currently logged-in user
     */
    private void printDashboardMenu(User user) {
        System.out.println(BORDER);
        System.out.println("  DASHBOARD — Logged in as: " + user.getDisplayName()
                + " [" + user.getRole() + "]");
        System.out.println(BORDER);
        System.out.println("  [1]  View Account Info");
        System.out.println("  [2]  Check Notifications");
        System.out.println("  [3]  Logout");
        System.out.println(BORDER);
        System.out.print("  Enter your choice: ");
    }

    /**
     * Displays the logged-in user's account information.
     *
     * @param user The currently logged-in user
     */
    private void showAccountInfo(User user) {
        System.out.println(BORDER);
        System.out.println("  ACCOUNT INFORMATION");
        System.out.println(BORDER);
        System.out.println("  Username     : " + user.getUsername());
        System.out.println("  Full Name    : " + user.getDisplayName());
        System.out.println("  Role         : " + user.getRole());
        System.out.println("  Status       : Active");
        System.out.println(BORDER);
        System.out.print("  Press Enter to go back...");
        scanner.nextLine();
        System.out.println();
    }

    // -------------------------------------------------------
    // Display Helpers
    // -------------------------------------------------------

    /**
     * Shows the welcome message after a successful login.
     *
     * @param user The user who logged in
     */
    private void displayLoginSuccessMessage(User user) {
        System.out.println();
        System.out.println(BORDER);
        System.out.println("  LOGIN SUCCESSFUL");
        System.out.println(BORDER);
        System.out.println("  Welcome, " + user.getDisplayName() + "!");
        System.out.println("  Role   : " + user.getRole());
        System.out.println("  Status : Access Granted");
        System.out.println(BORDER);
        System.out.println();
    }

    /**
     * Displays a lockout message when the system is locked due to
     * too many failed login attempts.
     */
    private void displayLockedMessage() {
        System.out.println();
        System.out.println(BORDER);
        System.out.println("  !! SYSTEM LOCKED !!");
        System.out.println(BORDER);
        System.out.println("  Too many failed login attempts.");
        System.out.println("  Access has been temporarily suspended.");
        System.out.println("  Please contact the system administrator.");
        System.out.println(BORDER);
        System.out.println();
    }

    /**
     * Displays the exit screen when the user chooses to close the system.
     */
    private void displayExitScreen() {
        System.out.println();
        System.out.println(BORDER);
        System.out.println("  ||       Thank you for using Java Secure Login System.      ||");
        System.out.println("  ||              Stay safe. Stay secure.                     ||");
        System.out.println(BORDER);
        System.out.println();
    }

    // -------------------------------------------------------
    // Input Helper
    // -------------------------------------------------------

    /**
     * Safely reads an integer from the Scanner.
     * Returns -1 on invalid (non-integer) input instead of crashing.
     *
     * @return Integer entered by user, or -1 on error
     */
    private int readIntInput() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Consume remaining newline
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the bad input
            System.out.println("  [!] Please enter a valid number.");
            return -1;
        }
    }
}