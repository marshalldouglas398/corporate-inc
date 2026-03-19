package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        scenario9();
        scenario8();
        scenarioSallyCreateAccount();
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
       // User newUser = interviewApp.createAccount("newuser", "password123", dob, "newuser@email.com");

       // if (newUser != null) {
       //     System.out.println("Account creation successful");
       //     System.out.println("Username: " + newUser.getUsername());
       //     System.out.println("Date of Birth: " + newUser.getBirthDate());
       //     System.out.println("Email: " + newUser.getEmail());
      //      System.out.println("Role: " + newUser.getRole());
      //  } else {
       //     System.out.println("Account creation failed");
       // }
    }

    /**
     * Sixth scenario: attempt to create an invalid user account (blank username)
     */
    public void scenario6() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 6: Create invalid user account (blank username)");

        Date dob = new Date();
        //User invalidUser = interviewApp.createAccount("", "password123", dob, "invaliduser@email.com");

       // if (invalidUser != null) {
        //    System.out.println("Account creation unexpectedly succeeded");
       // } else {
       //     System.out.println("Account creation failed as expected due to invalid username");
       // }
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
        
        //Sally attempts to create an account with an existing username, which should fail
        Date sallyDOB = new Date();
        User user = interviewApp.createAccount("ssparrow", "sallypassword", sallyDOB, "ssparrow@gmail.com", "356432334", "Computer Science");
        if (user != null) {
            System.out.println("Sally's account was successfully created");
        } else {
            System.out.println("Sally's account creation failed, ssparrow account already exists");
        }

        //Sally creates an editor account successfully and logins in

        //Sally creates a new question
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE240);
        interviewApp.addQuestion("Longest Subarray with given Sum", user, hints, QuestionType.TECHNICAL, disciplines, Difficulty.MEDIUM, courses, 3, "Given an integer array nums and an integer sum, return the length of the longest contiguous subarray whose total equals k.\nNote: the array can contain negative numbers");

        //Sally adds two solutions to her question
    }

public void scenarioSallyCreateAccount() {
    System.out.println("--------------------------------");
    System.out.println("Sally Successfully Creates an Account:");
     Date dob = new Date();
    User sallyUser = interviewApp.createAccount("SallySullivan", "ILoveComputerScience", dob, "sallysul@email.com", "356432334", "Computer Science");
    interviewApp.toEditor(sallyUser);
   // interviewApp.logout(sallyUser);
    }

    public void scenario9() {
        System.out.println("--------------------------------");
        System.out.println("Scenario 9: Jimmy Bauer's Session");

        User user = interviewApp.login("Jimmy_Bauer", "MyNameIsJimmyBauer");
        if (user == null) {
            System.out.println("Login failed for Jimmy_Bauer");
            return;
        }
        System.out.println("Login successful");
        System.out.println("Username   : " + user.getUsername());
        System.out.println("Email      : " + user.getEmail());

        Student jimmy = (Student) user;
        System.out.println("Courses Taken: " + jimmy.getCoursesTaken());

        int courseCount = jimmy.getCoursesTaken().size();
        Difficulty targetDifficulty;
        if (courseCount >= 4) {
            targetDifficulty = Difficulty.HARD;
        } else if (courseCount >= 3) {
            targetDifficulty = Difficulty.MEDIUM;
        } else {
            targetDifficulty = Difficulty.EASY;
        }
        System.out.println("\n--- Daily Challenge ---");
        System.out.println("Courses taken: " + courseCount + " -> Difficulty: " + targetDifficulty);

        ArrayList<Question> mediumQuestions = interviewApp.filterQuestion(
                null, null, null, targetDifficulty, null, null);

        Question dailyChallenge = null;
        for (Question q : mediumQuestions) {
            if (!jimmy.getQuestionsAnswered().contains(q.getId())) {
                dailyChallenge = q;
                break;
            }
        }
        if (dailyChallenge == null) {
            System.out.println("No unsolved " + targetDifficulty + " questions available.");
            interviewApp.logout(user);
            return;
        }
        System.out.println("Today's Challenge: \"" + dailyChallenge.getTitle() + "\"");

        System.out.println("\n=== Question Details ===");
        System.out.println("Title      : " + dailyChallenge.getTitle());
        System.out.println("Difficulty : " + dailyChallenge.getDifficulty());
        System.out.println("Type       : " + dailyChallenge.getType());
        System.out.println("Discipline : " + dailyChallenge.getDiscipline());
        System.out.println("Courses    : " + dailyChallenge.getCourse());
        System.out.println("Tags       : " + dailyChallenge.getTag());
        System.out.println("\nDescription:\n" + dailyChallenge.getDescription());

        System.out.println("\n--- Sections ---");
        for (int i = 0; i < dailyChallenge.getSections().size(); i++) {
            Section s = dailyChallenge.getSections().get(i);
            System.out.println("[Section " + (i + 1) + "] " + s.getTitle());
            System.out.println("  " + s.getDescription());
            if (s.getCode() != null && !s.getCode().isEmpty()) {
                System.out.println("  Code:\n" + s.getCode());
            }
        }

        System.out.println("\n--- Hints ---");
        for (int i = 0; i < dailyChallenge.getHints().size(); i++) {
            System.out.println("  Hint " + (i + 1) + ": " + dailyChallenge.getHints().get(i));
        }

        System.out.println("\n--- Solutions ---");
        ArrayList<Comment> solutions = new ArrayList<>();
        for (Comment c : dailyChallenge.getComments()) {
            if (c.getTags().contains(CommentTag.SOLUTION)) {
                solutions.add(c);
            }
        }
        System.out.println("Found " + solutions.size() + " solution(s).");
        for (int i = 0; i < solutions.size(); i++) {
            Comment sol = solutions.get(i);
            System.out.println("\n[Solution " + (i + 1) + "] " + sol.getTitle());
            System.out.println("  Author : " + sol.getAuthor().getUsername());
            System.out.println("  Rating : " + sol.getRating());
            System.out.println("  " + sol.getComment());
            for (Section s : sol.getSections()) {
                System.out.println("  -- " + s.getTitle() + " --");
                System.out.println("  " + s.getDescription());
                if (s.getCode() != null && !s.getCode().isEmpty()) {
                    System.out.println("  Code:\n" + s.getCode());
                }
            }
        }

        System.out.println("\n--- Jimmy is confused - writing a comment on Solution 2 ---");
        if (solutions.size() < 2) {
            System.out.println("Not enough solutions to comment on the second one.");
        } else {
            Comment secondSolution = solutions.get(1);
            String jimmysQuestion = "I understand using a while loop to traverse the tree, but I am confused about why we need to track the parent node separately. Could you walk through what would happen if we tried to attach the new node without keeping track of the parent?";

            ArrayList<CommentTag> commentTags = new ArrayList<>();
            commentTags.add(CommentTag.QUESTION);

            Comment jimmysComment = new Comment(
                    "Question about Solution 2",
                    jimmysQuestion,
                    jimmy,
                    commentTags,
                    new ArrayList<>(),
                    false,
                    0
            );

            secondSolution.addReply(jimmysComment);

            System.out.println("Comment posted on: \"" + secondSolution.getTitle() + "\"");
            System.out.println("  Author  : " + jimmy.getUsername());
            System.out.println("  Date    : " + new Date());
            System.out.println("  Comment : " + jimmysQuestion);
        }

        String outputFile = "jimmy_review.txt";
        printQuestionToFile(dailyChallenge, outputFile);

        System.out.println("\n=== Searching for \"Binary Search Tree\" ===");
        String searchTerm = "Binary Search Tree";
        ArrayList<Question> searchResults = interviewApp.searchQuestions(searchTerm);
        System.out.println("Found " + searchResults.size() + " question(s) matching \"" + searchTerm + "\":");

        for (int i = 0; i < searchResults.size(); i++) {
            Question q = searchResults.get(i);
            System.out.println("\n[Result " + (i + 1) + "] " + q.getTitle());
            System.out.println("  Difficulty : " + q.getDifficulty());
            System.out.println("  Type       : " + q.getType());
            System.out.println("  Description: " + q.getDescription());
        }

        boolean loggedOut = interviewApp.logout(user);
        System.out.println("\nJimmy logged out: " + loggedOut);
        System.out.println("================================");
    }

    /**
     * Prints a question and all its details to a well-formatted text file.
     * @param question the question to print
     * @param filename the output file path
     */
    private void printQuestionToFile(Question question, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("================================================");
            pw.println("QUESTION: " + question.getTitle());
            pw.println("================================================");
            pw.println("Difficulty : " + question.getDifficulty());
            pw.println("Type       : " + question.getType());
            pw.println("Discipline : " + question.getDiscipline());
            pw.println("Courses    : " + question.getCourse());
            pw.println("Tags       : " + question.getTag());
            pw.println("Rating     : " + question.getRating());
            pw.println();
            pw.println("--- Description ---");
            pw.println(question.getDescription());
            pw.println();

            pw.println("--- Sections ---");
            for (int i = 0; i < question.getSections().size(); i++) {
                Section s = question.getSections().get(i);
                pw.println("[Section " + (i + 1) + "] " + s.getTitle());
                pw.println(s.getDescription());
                if (s.getCode() != null && !s.getCode().isEmpty()) {
                    pw.println("Code:");
                    pw.println(s.getCode());
                }
                pw.println();
            }

            pw.println("--- Hints ---");
            for (int i = 0; i < question.getHints().size(); i++) {
                pw.println("  Hint " + (i + 1) + ": " + question.getHints().get(i));
            }
            pw.println();

            pw.println("--- Solutions & Comments ---");
            ArrayList<Comment> comments = question.getComments();
            for (int i = 0; i < comments.size(); i++) {
                printCommentToFile(pw, comments.get(i), i + 1, 0);
            }

            pw.println("================================================");
            pw.println("End of Question");
            pw.println("================================================");

            System.out.println("\nQuestion printed to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing question to file: " + e.getMessage());
        }
    }

    /**
     * Recursively prints a comment and its replies to the PrintWriter, indented by depth.
     */
    private void printCommentToFile(PrintWriter pw, Comment comment, int index, int depth) {
        String indent = "  ".repeat(depth);
        pw.println(indent + "[Comment " + index + "] " + comment.getTitle());
        pw.println(indent + "  Author : " + comment.getAuthor().getUsername());
        pw.println(indent + "  Tags   : " + comment.getTags());
        pw.println(indent + "  Rating : " + comment.getRating());
        pw.println(indent + "  " + comment.getComment());
        for (Section s : comment.getSections()) {
            pw.println(indent + "  -- " + s.getTitle() + " --");
            pw.println(indent + "  " + s.getDescription());
            if (s.getCode() != null && !s.getCode().isEmpty()) {
                pw.println(indent + "  Code:");
                pw.println(indent + "  " + s.getCode().replace("\n", "\n" + indent + "  "));
            }
        }
        if (!comment.getReplies().isEmpty()) {
            pw.println(indent + "  Replies:");
            for (int i = 0; i < comment.getReplies().size(); i++) {
                printCommentToFile(pw, comment.getReplies().get(i), i + 1, depth + 2);
            }
        }
        pw.println();
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }
}
