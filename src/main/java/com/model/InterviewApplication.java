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
    public User login(String username, String password) { //to do
        User loginUser = userlist.login(username, password);
        return loginUser;
    }
    /**
     * Logs a user out of the application
     * @param user the user you want to log out
     * @return true if the user was logged out, false otherwise
     */
    public boolean logout(User user) { // to do
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
    public User createAccount(String username, String password, Date dateOfBirth, String email) { // to do
        return user;
    }
    /**
     * Deletes a user account
     * @param user the user you want to delete
     * @return true if the user was deleted, false otherwise
     */
    public boolean deleteUser(User user) { // to do
        return true;
    }
    /**
     * Edits a user account
     * @param user the user you want to edit
     * @return true if the user was edited, false otherwise
     */
    public boolean editUser(User user) { // to do
        return true;
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
    public User findUser(String username) { // to do
        return null;
    }
    /**
     * Adds a question to the list of questions
     * @return true if the question was added, false otherwise
     */
    public boolean addQuestion() { // to do
        return true;
    }
    /**
     * Edits a question in the list of questions
     * @param question the question you want to edit
     * @return true if the question was edited, false otherwise
     */
    public boolean editQuestion(Question question) { // to do
        return true;
    }
    /**
     * Rates a question
     * @param question the question you want to rate
     * @param num the rating value
     * @return true if the question was rated, false otherwise
     */
    public boolean rateQuestion(Question question, Double num) { // to do
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
        return true;
    }
}
