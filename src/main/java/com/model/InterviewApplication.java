package com.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * This class represents an the actions you can take in the inteview application
 * @Author Eden Moore
 */
public class InterviewApplication {
    private QuestionList questionList;
    private UserList userlist;
    private User user;
    private Question currentQuestion;

    public InterviewApplication() {
        this.questionList = QuestionList.getInstance();
        this.userlist = UserList.getInstance();
    }
    /**
     * Logs in a user based on username and password
     * @param username username of the user you want to log in
     * @param password password of the user you want to log in
     * @return the user if found, null otherwise
     */
    public User login(String username, String password) {
        user = userlist.login(username, password);
        return user;
    }
    /**
     * Logs a user out of the application
     * @param user the user you want to log out
     * @return true if the user was logged out, false otherwise
     */
    public boolean logout(User user) {
        userlist.save();
        return true;
    } 
    /**
     * Creates an account for a user
     * @param username username of the new user
     * @param password password of the new user
     * @param dateOfBirth date of birth of the new user
     * @param email email of the new user
     * @return the new user account if created, null otherwise
     */
    public User createAccount(String username, String password, Date dateOfBirth, String email) {
        // Basic validation: all fields required
        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || email == null || email.isBlank()
                || dateOfBirth == null) {
            return null;
        }

        // Username must be unique
        if (userlist.searchUser(username) != null) {
            return null;
        }

        User newUser = new Student(username, password, dateOfBirth, email, "USCID", "Major");
        userlist.getUsers().add(newUser);
        userlist.save();
        return newUser;
    }
    /**
     * Deletes a user account
     * @param user the user you want to delete
     * @return true if the user was deleted, false otherwise
     */
    public boolean deleteUser(User user) {
        if(this.user.getRole().equals("Admin")) {
            ArrayList<User> users = userlist.getUsers();
            users.remove(userlist.getUser(user.getID()));
            userlist.save();
            return true;
        }
        return false;
    }
    /**
     * Edits a user account
     * @param user the user you want to edit
     * @return true if the user was edited, false otherwise
     */
    public boolean editUser(User user) { 
        if(this.user.getRole().equals("Admin")) {
            ArrayList<User> users = userlist.getUsers();
            users.remove(userlist.getUser(user.getID()));
            users.add(user);
            userlist.save();
            return true;
        }
        return false;
    }
    /**
     * Filters the list of questions based on the given parameters
     * @param questionList the list of questions you want to filter
     * @param qtype the type of question you want to filter by
     * @param d the discipline you want to filter by
     * @param diff the difficulty you want to filter by
     * @param c the course you want to filter by
     * @param qt the tag you want to filter by
     * @return the filtered list of questions
     */
    public ArrayList<Question> filterQuestion(ArrayList<Question> questionList, QuestionType qtype,
        Discipline d, Difficulty diff, Course c, QuestionTag qt) { // to do
            return questionList;
    }
    /**
     * Finds a user based on username
     * @param username username of the user you want to find
     * @return the user if found, null otherwise
     */
    public User findUser(String username) {
        return userlist.searchUser(username);
    }
    /**
     * Adds a question to the list of questions
     * @return true if the question was added, false otherwise
     */
    public boolean addQuestion(String title, User author, ArrayList<String> hints, QuestionType type, ArrayList<Discipline> discipline, Difficulty difficulty, ArrayList<Course> course, int numSections) {
        if(this.user.getRole().equals("Admin") || this.user.getRole().equals("Editor")) {
            questionList.addQuestion(title, author,hints,type,discipline,difficulty,course, numSections);
            return true;
        }
        return false;
    }
    /**
     * Edits a question in the list of questions
     * @param question the question you want to edit
     * @return true if the question was edited, false otherwise
     */
    public boolean editQuestion(Question question) {
        System.out.println("How would you like to edit the question? (title, hints, type, discipline, difficulty, course)");
        Scanner k = new Scanner(System.in);
        String input = k.nextLine();
        switch(input) {
            case "title":
                System.out.println("Enter the new title:");
                String title = k.nextLine();
                question.setTitle(title);
                break;
            case "hints":
                System.out.println("Enter the new hints (separated by commas):");
                String hintsInput = k.nextLine();
                for(String hint : hintsInput.split(",")) {
                    question.getHints().add(hint.trim());
                }
                break;
            case "type":
                System.out.println("Enter the new type (Coding, Behavioral, or Other):");
                String typeInput = k.nextLine();
                QuestionType type = QuestionType.valueOf(typeInput);
                question.setType(type);
                break;
            case "discipline":
                System.out.println("Enter the new disciplines (separated by commas):");
                String disciplineInput = k.nextLine();
                for(String d : disciplineInput.split(",")) {
                    question.getDiscipline().add(Discipline.valueOf(d.trim()));
                }
                break;
            case "difficulty":
                System.out.println("Enter the new difficulty (Easy, Medium, or Hard):");
                String difficultyInput = k.nextLine();
                Difficulty difficulty = Difficulty.valueOf(difficultyInput);
                question.setDifficulty(difficulty);
                break;
            case "course":
                System.out.println("Enter the new courses (separated by commas):");
                String courseInput = k.nextLine();
                for(String c : courseInput.split(",")) {
                    question.getCourse().add(Course.valueOf(c.trim()));
                }
                break;
            default:
                System.out.println("Invalid input.");
        }
        return true;
    }
    /**
     * Deletes a question from the list of questions
     * @param question question you want to delete
     * @return true if deleted, false if not
     */
    public boolean deleteQuestion(Question question) {
        if(this.user.getRole().equals("Admin") || this.user.getRole().equals("Editor")) {
            questionList.getQuestions().remove(question);
            questionList.save();
            return true;
        }
        return false;
    }
    /**
     * Rates a question
     * @param question the question you want to rate
     * @param num the rating value
     * @return true if the question was rated, false otherwise
     */
    public boolean rateQuestion(Question question, Double num) {
        questionList.getQuestion(question.getId()).addRating(num);
        return true;
    }
    /**
     * Selects a question to be the current question
     * @param question the question you want to select
     * @return true if the question was selected, false otherwise
     */
    public boolean selectQuestion(Question question) {
        this.currentQuestion = question;
        return true;
    }
    /**
     * Adds a comment to a question
     * @param question the question you want to add a comment to
     * @return true if the comment was added, false otherwise
     */
    public boolean addCommentQ(Question question) {
        System.out.println("Enter the title of your comment:");
        Scanner k = new Scanner(System.in);
        String commentTitle = k.nextLine();
        System.out.println("Enter the content of your comment:");
        String commentContent = k.nextLine();
        System.out.println("Enter the tags for your comment (separated by commas):");
        String commentTagsInput = k.nextLine();
        ArrayList<CommentTag> commentTags = new ArrayList<>();
        for(String tag : commentTagsInput.split(",")) {
            commentTags.add(CommentTag.valueOf(tag.trim()));
        }
        System.out.println("Would you like to add a section to your comment? (y/n)");
        String sectionInput = k.nextLine();
        boolean flag = sectionInput.equalsIgnoreCase("y");
        ArrayList<Section> commentSections = new ArrayList<>();
        while ( flag ) {
            System.out.println("Enter the title of the section:");
            String sectionTitle = k.nextLine();
            System.out.println("Would you like to add a description to your section? (y/n)");
            String descriptionInput = k.nextLine();
            String sectionDescription = "";
            if (descriptionInput.equalsIgnoreCase("y")) {
                System.out.println("Enter the description of the section:");
                sectionDescription = k.nextLine();
            }
            System.out.println("Would you like to add a file to your section? (y/n)");
            String fileInput = k.nextLine();
            File sectionFile = null;
            if (fileInput.equalsIgnoreCase("y")) {
                System.out.println("Enter the file path:");
                String filePath = k.nextLine();
                sectionFile = new File(filePath);
            }
            System.out.println("Would you like to add code to your section? (y/n)");
            String codeInput = k.nextLine();
            String sectionCode = "";
            if (codeInput.equalsIgnoreCase("y")) {
                System.out.println("Enter the code for the section:");
                sectionCode = k.nextLine();
            }
            Section commentSection = new Section(sectionTitle, sectionDescription, sectionFile, sectionCode);
            commentSections.add(commentSection);
            System.out.println("Would you like to add another section to your comment? (y/n)");
            String anotherSectionInput = k.nextLine();
            flag = anotherSectionInput.equalsIgnoreCase("y");
        }
        Comment comment = new Comment(commentTitle, commentContent, user, commentTags, commentSections, question.isAuthor(((Editor)(new User(this.user)))));
        question.addComment(comment);
        return true;
    }
    /**
     * Adds a comment to a comment
     * @param comment the comment you want to add a comment to
     * @return true if the comment was added, false otherwise
     */
    public boolean addCommentC(Comment comment) {
        return true;
    }
    /**
     * Edits a comment on a question
     * @param comment the comment you want to edit
     * @return true if the comment was edited, false otherwise
     */
    public boolean editComment(Comment comment) {
        return true;
    }
    /**
     * Rates a comment on a question
     * @param comment comment you want to rate
     * @param num what you want to rate the comment
     * @return true if the comment was rated, false otherwise
     */
    public boolean rateComment(Comment comment, Double rating) {
        comment.rateComment(rating);
        return true;
    }
}
