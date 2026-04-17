package com.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents an the actions you can take in the inteview application
 * @Author Eden Moore
 */
public class InterviewApplication {
    private QuestionList questionList;
    private UserList userlist;
    private User user;
    private Question currentQuestion;

    /**
     * Constructor for the InterviewApplication class. Initializes the question list and user list as singletons.
     */
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
        questionList.save();
        return true;
    } 
    /**
     * Creates an account for a user
     * @param username username of the new user
     * @param password password of the new user
     * @param dateOfBirth date of birth of the new user
     * @param email email of the new user
     * @param USCID USC ID of the new user
     * @param major major of the new user
     * @return the new user account if created, null otherwise
     */
    public User createAccount(String username, String password, Date dateOfBirth, String email, String USCID, String major) {
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

        User newUser = new Student(username, password, dateOfBirth, email, USCID, major);
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
        Discipline d, Difficulty diff, Course c, QuestionTag qt) {
        ArrayList<Question> filteredList = this.questionList.filterQuestion(this.questionList.getQuestions(), qtype, d, diff, c, qt);
        return filteredList;
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
     * @param title title of the question
     * @param author author of the question
     * @param hints hints for the question
     * @param type type of the question
     * @param discipline discipline of the question
     * @param difficulty difficulty of the question
     * @param course course of the question
     * @param description description of the question
     * @return true if the question was added, false otherwise
     */
    public boolean addQuestion(String title, User author, ArrayList<String> hints, QuestionType type, ArrayList<Discipline> discipline, Difficulty difficulty, ArrayList<Course> course, String description, QuestionTag qt) {
        if(author.getRole().equals("Admin") || author.getRole().equals("Editor")) {
            questionList.addQuestion(title, author, hints, type, discipline, difficulty, course, description, qt);
            return true;
        }
        return false;
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
     * Rates a comment on a question
     * @param comment comment you want to rate
     * @param rating the rating value
     * @return true if the comment was rated, false otherwise
     */
    public boolean rateComment(Comment comment, Double rating) {
        comment.rateComment(rating);
        return true;
    }
    
    /**
     * Promotes a student user to an editor role
     * @param user the user you want to promote
     * @return true if the user was promoted, false otherwise
     */
    public boolean toEditor(User user) {
        if(user.getRole().equals("Student")) {
            Student student = (Student) user;
            Editor editor = student.toEditor();
            userlist.getUsers().remove(user);
            userlist.getUsers().add(editor);
            userlist.save();
            return true;
        }
        return false;
    }

    /**
     * Searches all questions whose title contains the given keyword (case-insensitive)
     * @param keyword the search term to look for in question titles
     * @return list of questions whose title contains the keyword
     */
    public ArrayList<Question> searchQuestions(String keyword) {
        ArrayList<Question> results = new ArrayList<>();
        for (Question q : questionList.getQuestions()) {
            if(q.getTitle() == null) continue; // Skip questions with null titles
            if (q.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(q);
            }
        }
        return results;
    }

    /**
     * Saves userList
     */
    public void saveUsers() {
    userlist.save();
    }

    /**
     * saves questionList
     */
    public void saveQuestions() {
        questionList.save();
    }
}
