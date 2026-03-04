package com.model;

import java.util.Date;

/**
 * This class represents the driver, where our program is tested through scenarios.
 * @author Ainsley Weaver
 */

public class Driver {

    private InterviewApplication interviewApp;

    Driver() {
        interviewApp = new InterviewApplication();
    }

    /**
     * Runs the scenarios
     */
    public void run() {
        scenario1();
        scenario2();
        //scenario3();
    }

    /**
     * First scenario of a user logging in, where the login should fail
     */
    public void scenario1() {
        if (interviewApp.login("jhardee", "password123") == null) {
            System.out.println("Login failed");
        } else {
            System.out.println("Login successful");
        }
    }

    /**
     * Second scenario of a user logging in, where the login should succeed
     */
    public void scenario2() {
        if (interviewApp.login("student", "password") == null) {
            System.out.println("Login failed");
        } else {
            System.out.println("Login successful");
        // Test logging out and getting user characteristics
        // Get the logged in user
        User user = interviewApp.login("student", "password");
        if (user != null) {
            // Print user characteristics
            System.out.println("Username: " + user.getUsername());
            System.out.println("Date of Birth: " + user.getBirthDate());
            // Logout
            boolean loggedOut = interviewApp.logout(user);
            System.out.println("User logged out: " + loggedOut);
        }
        }
    }

    /**
     * Third scenario, where a user creates an account
     * WIP 
     */
    public void scenario3() {
        interviewApp.createAccount("jhardee", "password", new Date(), "jhardee@email.com", "Student");
        interviewApp.createAccount("amsith", "password", new Date(), "asmith@email.com", "Student");
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }
}
