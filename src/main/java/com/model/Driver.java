package com.model;

import java.util.ArrayList;
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
        scenario3();
        scenario4();
        scenario5();
        scenario6();
        scenario7();
        scenario8();
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
                System.out.println("USC ID: " + student.getUSCID());
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

        /**
     * Fifth scenario: create a valid user account
     */
    public void scenario5() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 5: Create valid user account");

        Date dob = new Date();
        User newUser = interviewApp.createAccount("newuser", "password123", dob, "newuser@email.com");

        if (newUser != null) {
            System.out.println("Account creation successful");
            System.out.println("Username: " + newUser.getUsername());
            System.out.println("Date of Birth: " + newUser.getBirthDate());
            System.out.println("Email: " + newUser.getEmail());
            System.out.println("Role: " + newUser.getRole());
        } else {
            System.out.println("Account creation failed");
        }
    }

    /**
     * Sixth scenario: attempt to create an invalid user account (blank username)
     */
    public void scenario6() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 6: Create invalid user account (blank username)");

        Date dob = new Date();
        User invalidUser = interviewApp.createAccount("", "password123", dob, "invaliduser@email.com");

        if (invalidUser != null) {
            System.out.println("Account creation unexpectedly succeeded");
        } else {
            System.out.println("Account creation failed as expected due to invalid username");
        }
    }
    public void scenario7() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 7: Add question as editor");
        User user = interviewApp.login("editor", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("Just open your mouth");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE240);
        interviewApp.addQuestion("How to talk to people?", user, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.MEDIUM, courses, 1);
        interviewApp.logout(user);
    }

    public void scenario8() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 8: Sally the Editor");
        Date sallyDOB = new Date();
        User user = interviewApp.createAccount("ssparrow", "sallypassword", sallyDOB, "ssparrow@gmail.com");
        if (user != null) {
            System.out.println("Sally's account was successfully created");
        } else {
            System.out.println("Sally's account creation failed, ssparrow account already exists");
        }
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }
}
