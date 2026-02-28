package com.model;

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
    public User login(String username, String password) { //to do
        return null;
    }
    public boolean logout(User user) { // to do
        return true;
    } 
    public User createAccount(String username, String password, Date dateOfBirth, String email) { // to do
        return user;
    }
    public boolean deleteUser(User user) { // to do
        return true;
    }
    public boolean editUser(User user) { // to do
        return true;
    }
}
