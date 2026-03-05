package com.model;

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
        scenario3();
        scenario4();
    }

    /**
     * First scenario of a user logging in, where the login should fail
     */
    public void scenario1() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 1: Login failed");
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
        System.out.println("--------------------------------");
        System.out.println("Scenario 2: Login successful for student");
        if (interviewApp.login("student", "password") == null) {
            System.out.println("Login failed");
        } else {
            System.out.println("Login successful");
        }
        User user = interviewApp.login("student", "password");
        if (user != null) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Date of Birth: " + user.getBirthDate());
            System.out.println("Email: " + user.getEmail());

            // Student-specific info
            if (user instanceof Student) {
                Student student = (Student) user;
                System.out.println("USC ID: " + student.getUscID());
                System.out.println("Major: " + student.getMajor());
                System.out.println("Courses Taken: " + student.getCoursesTaken());
            }
            // Logout
            boolean loggedOut = interviewApp.logout(user);
            System.out.println("User logged out: " + loggedOut);
        }
    }

    /**
     * Third scenario of a user logging in as an editor, where the login should succeed
     */
    public void scenario3() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 3: Login successful for editor");
        if (interviewApp.login("editor", "password") == null) {
            System.out.println("Login failed");
        } else {
            System.out.println("Login successful");
        }

        User user = interviewApp.login("editor", "password");
        if (user != null) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Date of Birth: " + user.getBirthDate());
            System.out.println("Email: " + user.getEmail());

            // Editor-specific info
            if (user instanceof Editor) {
                Editor editor = (Editor) user;
                System.out.println("Role: " + editor.getRole());
                System.out.println("Questions Made: " + editor.getQuestionsMade());
            }
            boolean loggedOut = interviewApp.logout(user);
            System.out.println("User logged out: " + loggedOut);
        }
    }

    /**
     * Fourth scenario of a user logging in as an admin, where the login should succeed
     */
    public void scenario4() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 4: Login successful for admin");
        if (interviewApp.login("admin", "password") == null) {
            System.out.println("Login failed");
        } else {
            System.out.println("Login successful");
        }

        User user = interviewApp.login("admin", "password");
        if (user != null) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Date of Birth: " + user.getBirthDate());
            System.out.println("Email: " + user.getEmail());

            // Admin-specific info
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                System.out.println("Role: " + admin.getRole());
                System.out.println("Questions Made: " + admin.getQuestionsMade());
            }
            boolean loggedOut = interviewApp.logout(user);
            System.out.println("User logged out: " + loggedOut);
        }
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }
}
