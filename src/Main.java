/**
 * Main.java
 * Entry point for the Java Secure Login System.
 * Creates the LoginSystem instance and launches the application.
 *
 * Author: Internship Submission
 * Project: Login System
 *
 * HOW TO COMPILE AND RUN:
 *   javac *.java
 *   java Main
 *
 * Demo credentials (use any of the following):
 * +----------+-------------+----------------------+---------+
 * | Username | Password    | Display Name         | Role    |
 * +----------+-------------+----------------------+---------+
 * | admin    | admin@123   | System Administrator | ADMIN   |
 * | rahul    | rahul#2024  | Rahul Sharma         | USER    |
 * | priya    | priya$456   | Priya Patel          | USER    |
 * | vikram   | vik@789     | Vikram Nair          | USER    |
 * | manager  | mgr@pass1   | Anita Desai          | MANAGER |
 * +----------+-------------+----------------------+---------+
 */

public class Main {

    public static void main(String[] args) {

        // Instantiate the login system
        LoginSystem loginSystem = new LoginSystem();

        // Launch the application — this runs the entire program
        loginSystem.launch();
    }
}