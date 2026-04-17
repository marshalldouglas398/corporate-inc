package com.model;

/**
 * This class represents the driver, where our program is tested through scenarios.
 * @author Ainsley Weaver
 */

public class Driver {

    private InterviewApplication interviewApp;

    /**
     * Constructor initializes the InterviewApplication instance
     */
    Driver() {
        interviewApp = new InterviewApplication();
    }

    /**
     * Runs the scenarios
     */
    public void run() {
        //scenario1();
        //scenario2();
        scenario3();
    }

    public void scenario3() {
        User user = interviewApp.login("editor", "password");
        interviewApp.addQuestion("test", user, null, null, null, null, null, null, QuestionTag.ANALYZE_COMPLIXITY);
    }

    /**
     * Scenario 1: Sally the Editor
      - Sally attempts to create an account with an existing username
      - Sally creates an editor account successfully and logins in
      - Sally creates a new question and adds two solutions to her question
      - Sally logs out
     */
    // public void scenario1() {
    //     System.out.println("--------------------------------");
    //     System.out.println("Scenario 8: Sally the Editor");
        
    //     //Sally attempts to create an account with an existing username, which should fail
    //     Date sallyDOB = new Date();
    //     User user = interviewApp.createAccount("ssparrow", "sallypassword", sallyDOB, "ssparrow@gmail.com", "356432334", "Computer Science");
    //     if (user != null) {
    //         System.out.println("Sally's account was successfully created");
    //     } else {
    //         System.out.println("Sally's account creation failed, ssparrow account already exists");
    //         System.out.println("Existing user: " + interviewApp.findUser("ssparrow").getUsername() + "\nExisting Email: " + interviewApp.findUser("ssparrow").getEmail());
    //     }

    //     //Sally creates an editor account successfully and logins in
    //     Date dob = new Date();
    //     User sallyUser = interviewApp.createAccount("SallySullivan", "ILoveComputerScience", dob, "sallysul@email.com", "356432334", "Computer Science");
    //     System.out.println("\nSally's current role: " + sallyUser.getRole());
    //     interviewApp.toEditor(sallyUser);
    //     sallyUser = interviewApp.login("SallySullivan", "ILoveComputerScience");
    //     if (sallyUser != null) {
    //         System.out.println("\nLogin successful");
    //         System.out.println("New Username    : " + sallyUser.getUsername());
    //         System.out.println("New Email       : " + sallyUser.getEmail());
    //         System.out.println("Updated Role    : " + sallyUser.getRole());
    //     } else {
    //         System.out.println("Sally failed to log in");
    //     }

    //     //Sally creates a new question
    //     ArrayList<String> hints = new ArrayList<>();
    //     hints.add("");
    //     ArrayList<Discipline> disciplines = new ArrayList<>();
    //     disciplines.add(Discipline.COMPSCI);
    //     ArrayList<Course> courses = new ArrayList<>();
    //     courses.add(Course.CSCE240);
    //     interviewApp.addQuestion("Longest Subarray with given Sum", sallyUser, hints, QuestionType.TECHNICAL, disciplines, Difficulty.MEDIUM, courses, "Given an integer array nums and an integer sum, return the length of the longest contiguous subarray whose total equals k.\nNote: the array can contain negative numbers");
    //     Question sallysQuestion = interviewApp.searchQuestions("Longest Subarray with given Sum").get(0);
    //     sallysQuestion.addSection("Example 1:","Input: nums = [1,-1,5,-2,3], k = 3 \nOutput: 4 \nExplanation: \nThe subarray [1,-1,5,2] sums to 3 and has length 4.", null, null);
    //     sallysQuestion.addSection("Example 2:", "Input: nums = [-2,-1,2,1], k = 3 \nOutput: 2", null, null);
    //     sallysQuestion.addSection("Follow up Questions:", "- What is the time complexity of your algorithm? \n- Can you find a way to make your algorithm faster?", null, null);
        
    //     //below prints the question details to the console
    //     System.out.println("\n--- " + sallysQuestion.getTitle() + " ---");
    //     System.out.println(sallysQuestion.getDescription());
    //     System.out.println("Author: " + sallysQuestion.getAuthor().getUsername());
    //     System.out.println("\n" + sallysQuestion.getSections().get(0).getTitle());
    //     System.out.print(sallysQuestion.getSections().get(0).getDescription());
    //     System.out.println("\n\n" + sallysQuestion.getSections().get(1).getTitle());
    //     System.out.println(sallysQuestion.getSections().get(1).getDescription());
    //     System.out.println("\n" + sallysQuestion.getSections().get(2).getTitle());
    //     System.out.println(sallysQuestion.getSections().get(2).getDescription());

    //     //Sally adds two solutions to her question
    //     ArrayList<CommentTag> solution1Tags = new ArrayList<>();
    //     solution1Tags.add(CommentTag.EDITORSOLUTION);
    //     Comment solution1 = new Comment("Solution 1 - Brute Force Approach", "Try every possible subarray and compute its sum.", sallyUser, solution1Tags, new ArrayList<Section>(), true);
    //     solution1.addSection(new Section("Time Complexity:", "O(n^2)", null, null));
    //     solution1.addSection(new Section ("Code:", null, new File("src/main/java/com/model/files/solution1_brute_force_approach.png"), null));
    //     sallysQuestion.addComment(solution1);
    //     //below prints the first solution details to the console
    //     System.out.println("\n--- " + solution1.getTitle() + " ---");
    //     System.out.println(solution1.getComment());
    //     System.out.println("\n" + solution1.getSections().get(0).getTitle());
    //     System.out.print(solution1.getSections().get(0).getDescription());
    //     System.out.println(solution1.getSections().get(1).getTitle());
    //     System.out.println(solution1.getSections().get(1).getFile());
        
        
    //     ArrayList<CommentTag> solutionTags2 = new ArrayList<>();
    //     solutionTags2.add(CommentTag.EDITORSOLUTION);
    //     Comment comment2 = new Comment("Solution 2 - HashMap Verson:", "Idea is to keep track of each sum in a HashMap. \nIf: currentPrefixSum - previousPrefixSum = k \nThen: previousPrefixSum = currentPrefixSum - k \nSo while iterating: \n - Keep track of the prefix sum. \n - Store the first occurence of each prefix sum in a HashMap. \n - If (prefixSum - k) exists in the map; we found a valid subarray.", sallyUser, solutionTags2, new ArrayList<Section>(), true);
    //     File solution2 = new File("files/solution2_hashmap_version.png");
    //     comment2.addSection(new Section("Why store the first occurance?", "Because we want the longest subarray.\nTime Complexity: O(n) \nCode:", solution2 , null));
    //     sallysQuestion.addComment(comment2);
    //     //below prints the first solution details to the console
    //     System.out.println("\n--- " + comment2.getTitle() + " ---");
    //     System.out.println(comment2.getComment());
    //     System.out.println("\n" + comment2.getSections().get(0).getTitle());
    //     System.out.println(comment2.getSections().get(0).getDescription());
    //     System.out.println(comment2.getSections().get(0).getFile());

    //     interviewApp.logout(sallyUser);
    // }

    // /**
    //  * Scenario 2: Jimmy Bauer's Session
    //    - Jimmy logs into the system
    //    - Jimmy sees his 8 day streak
    //    - System gives him a daily challenge problem, tailored to him based on his skill level and preferences
    //    - Jimmy clicks on the question and views the solutions
    //    - Jimmy writes a comment on the second solution
    //    - Jimmy prints this question to a text file
    //    - Jimmy searches questions for "Binary Search Tree" and is presented two options
    //    - Daily streak has increased by 1
    //    - Jimmy logs out
    //  */
    // public void scenario2() {
    //     System.out.println("--------------------------------");
    //     System.out.println("Scenario 9: Jimmy Bauer's Session");

    //     User user = interviewApp.login("Jimmy_Bauer", "MyNameIsJimmyBauer");
    //     if (user == null) {
    //         System.out.println("Login failed for Jimmy_Bauer");
    //         return;
    //     }
    //     System.out.println("Login successful");
    //     System.out.println("Username   : " + user.getUsername());
    //     System.out.println("Email      : " + user.getEmail());

    //     Student jimmy = (Student) user;
    //     System.out.println("Daily Streak  : " + jimmy.getStreak() + " days");

    //     int courseCount = jimmy.getCoursesTaken().size();
    //     Difficulty targetDifficulty;
    //     if (courseCount >= 4) {
    //         targetDifficulty = Difficulty.HARD;
    //     } else if (courseCount >= 3) {
    //         targetDifficulty = Difficulty.MEDIUM;
    //     } else {
    //         targetDifficulty = Difficulty.EASY;
    //     }
    //     System.out.println("\n--- Daily Challenge ---");
    //     System.out.println("Courses taken: " + courseCount + " -> Difficulty: " + targetDifficulty);

    //     ArrayList<Question> mediumQuestions = interviewApp.filterQuestion(
    //             null, null, null, targetDifficulty, null, null);

    //     Question dailyChallenge = null;
    //     for (Question q : mediumQuestions) {
    //         if (!jimmy.getQuestionsAnswered().contains(q.getId())) {
    //             dailyChallenge = q;
    //             break;
    //         }
    //     }
    //     if (dailyChallenge == null) {
    //         System.out.println("No unsolved " + targetDifficulty + " questions available.");
    //         interviewApp.logout(user);
    //         return;
    //     }
    //     System.out.println("Today's Challenge: " + dailyChallenge.getTitle());

    //     System.out.println("\n-- Question Details --");
    //     System.out.println("Title      : " + dailyChallenge.getTitle());
    //     System.out.println("Difficulty : " + dailyChallenge.getDifficulty());
    //     System.out.println("Type       : " + dailyChallenge.getType());
    //     System.out.println("Discipline : " + dailyChallenge.getDiscipline());
    //     System.out.println("Courses    : " + dailyChallenge.getCourse());
    //     System.out.println("Tags       : " + dailyChallenge.getTag());
    //     System.out.println("\nDescription:\n" + dailyChallenge.getDescription());

    //     System.out.println("\n--- Sections ---");
    //     for (int i = 0; i < dailyChallenge.getSections().size(); i++) {
    //         Section s = dailyChallenge.getSections().get(i);
    //         System.out.println("[Section " + (i + 1) + "] " + s.getTitle());
    //         System.out.println("  " + s.getDescription());
    //         if (s.getCode() != null && !s.getCode().isEmpty()) {
    //             System.out.println("  Code:\n" + s.getCode());
    //         }
    //     }

    //     System.out.println("\n--- Hints ---");
    //     for (int i = 0; i < dailyChallenge.getHints().size(); i++) {
    //         System.out.println("  Hint " + (i + 1) + ": " + dailyChallenge.getHints().get(i));
    //     }

    //     System.out.println("\n--- Solutions ---");
    //     ArrayList<Comment> solutions = new ArrayList<>();
    //     for (Comment c : dailyChallenge.getComments()) {
    //         if (c.getTags().contains(CommentTag.SOLUTION)) {
    //             solutions.add(c);
    //         }
    //     }
    //     System.out.println("Found " + solutions.size() + " solutions.");
    //     for (int i = 0; i < solutions.size(); i++) {
    //         Comment sol = solutions.get(i);
    //         System.out.println("\n[Solution " + (i + 1) + "] " + sol.getTitle());
    //         System.out.println("  Author : " + sol.getAuthor().getUsername());
    //         System.out.println("  Rating : " + sol.getRating());
    //         System.out.println("  " + sol.getComment());
    //         for (Section s : sol.getSections()) {
    //             System.out.println("  -- " + s.getTitle() + " --");
    //             System.out.println("  " + s.getDescription());
    //             if (s.getCode() != null && !s.getCode().isEmpty()) {
    //                 System.out.println("  Code:\n" + s.getCode());
    //             }
    //         }
    //     }

    //     System.out.println("\nJimmy is confused - writing a comment on Solution 2");
    //     if (solutions.size() < 2) {
    //         System.out.println("Not enough solutions to comment on the second one.");
    //     } else {
    //         Comment secondSolution = solutions.get(1);
    //         String jimmysQuestion = "I understand using a while loop to traverse the tree, but I am confused about why we need to track the parent node separately. Could you walk through what would happen if we tried to attach the new node without keeping track of the parent?";

    //         ArrayList<CommentTag> commentTags = new ArrayList<>();
    //         commentTags.add(CommentTag.QUESTION);

    //         Comment jimmysComment = new Comment(
    //                 "Question about Solution 2",
    //                 jimmysQuestion,
    //                 jimmy,
    //                 commentTags,
    //                 new ArrayList<>(),
    //                 false
    //         );

    //         secondSolution.addReply(jimmysComment);

    //         System.out.println("Comment posted on: \"" + secondSolution.getTitle() + "\"");
    //         System.out.println("  Author  : " + jimmy.getUsername());
    //         System.out.println("  Date    : " + new Date());
    //         System.out.println("  Comment : " + jimmysQuestion);
    //     }

    //     String outputFile = "jimmy.txt";
    //     printQuestionToFile(dailyChallenge, outputFile);

    //     System.out.println("\nSearching for \"Binary Search Tree\"");
    //     String searchTerm = "Binary Search Tree";
    //     ArrayList<Question> searchResults = interviewApp.searchQuestions(searchTerm);
    //     System.out.println("Found " + searchResults.size() + " questions matching \"" + searchTerm + "\":");

    //     for (int i = 0; i < searchResults.size(); i++) {
    //         Question q = searchResults.get(i);
    //         System.out.println("\n[Result " + (i + 1) + "] " + q.getTitle());
    //         System.out.println("  Difficulty : " + q.getDifficulty());
    //         System.out.println("  Type       : " + q.getType());
    //         System.out.println("  Description: " + q.getDescription());
    //     }

    //     // Streak increment after completing the daily challenge session
    //     jimmy.incrementStreak();
    //     System.out.println("\nDaily streak updated: " + jimmy.getStreak() + " days");

    //     // 8. LOGOUT
    //     boolean loggedOut = interviewApp.logout(user);
    //     System.out.println("\nJimmy logged out: " + loggedOut);
    // }

    // /**
    //  * Prints a question and all its details to a well-formatted text file.
    //  * @param question the question to print
    //  * @param filename the output file path
    //  */
    // private void printQuestionToFile(Question question, String filename) {
    //     try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
    //         pw.println("QUESTION: " + question.getTitle());
    //         pw.println("Difficulty : " + question.getDifficulty());
    //         pw.println("Type       : " + question.getType());
    //         pw.println("Discipline : " + question.getDiscipline());
    //         pw.println("Courses    : " + question.getCourse());
    //         pw.println("Tags       : " + question.getTag());
    //         pw.println("Rating     : " + question.getRating());
    //         pw.println();
    //         pw.println("--- Description ---");
    //         pw.println(question.getDescription());
    //         pw.println();

    //         pw.println("--- Sections ---");
    //         for (int i = 0; i < question.getSections().size(); i++) {
    //             Section s = question.getSections().get(i);
    //             pw.println("[Section " + (i + 1) + "] " + s.getTitle());
    //             pw.println(s.getDescription());
    //             if (s.getCode() != null && !s.getCode().isEmpty()) {
    //                 pw.println("Code:");
    //                 pw.println(s.getCode());
    //             }
    //             pw.println();
    //         }

    //         pw.println("--- Hints ---");
    //         for (int i = 0; i < question.getHints().size(); i++) {
    //             pw.println("  Hint " + (i + 1) + ": " + question.getHints().get(i));
    //         }
    //         pw.println();

    //         pw.println("--- Solutions & Comments ---");
    //         ArrayList<Comment> comments = question.getComments();
    //         for (int i = 0; i < comments.size(); i++) {
    //             printCommentToFile(pw, comments.get(i), i + 1, 0);
    //         }

    //         System.out.println("\nQuestion printed to file: " + filename);
    //     } catch (IOException e) {
    //         System.out.println("Error writing question to file: " + e.getMessage());
    //     }
    // }

    // /**
    //  * Recursively prints a comment and its replies to the PrintWriter, indented by depth.
    //  */
    // private void printCommentToFile(PrintWriter pw, Comment comment, int index, int depth) {
    //     String indent = "  ".repeat(depth);
    //     pw.println(indent + "[Comment " + index + "] " + comment.getTitle());
    //     pw.println(indent + "  Author : " + comment.getAuthor().getUsername());
    //     pw.println(indent + "  Tags   : " + comment.getTags());
    //     pw.println(indent + "  Rating : " + comment.getRating());
    //     pw.println(indent + "  " + comment.getComment());
    //     for (Section s : comment.getSections()) {
    //         pw.println(indent + "  -- " + s.getTitle() + " --");
    //         pw.println(indent + "  " + s.getDescription());
    //         if (s.getCode() != null && !s.getCode().isEmpty()) {
    //             pw.println(indent + "  Code:");
    //             pw.println(indent + "  " + s.getCode().replace("\n", "\n" + indent + "  "));
    //         }
    //     }
    //     if (!comment.getReplies().isEmpty()) {
    //         pw.println(indent + "  Replies:");
    //         for (int i = 0; i < comment.getReplies().size(); i++) {
    //             printCommentToFile(pw, comment.getReplies().get(i), i + 1, depth + 2);
    //         }
    //     }
    //     pw.println();
    // }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }
}
