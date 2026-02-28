package com.model;
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
}
