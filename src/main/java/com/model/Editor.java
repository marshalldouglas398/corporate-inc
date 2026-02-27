package com.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents an editor, which is a type of user that can create, edit, and delete questions
 * @author Marshall Pigford
 */
public class Editor extends User {
    private ArrayList<Question> questionsMade;
    private String role;

    /**
     * Parameterized constructor for the Editor class
     * @param username The username of the editor
     * @param password The password of the editor
     * @param dateOfBirth The date of birth of the editor
     * @param email The email of the editor
     * @param role The role of the editor
     */
    public Editor(String username, String password, Date dateOfBirth, String email, String role) {
        super(username, password, dateOfBirth, email);
        this.role = role;
        this.questionsMade = new ArrayList<>();
    }

    /**
     * Adds a question to the list of questions made by the editor and the database
     * @param newQuestion The question to add to the list of questions made by the editor
     */
    public void addQuestion(Question newQuestion) {
        this.questionsMade.add(newQuestion);
        // Put new question in database
    }
    
    /**
     * Edits a question in the list of questions made by the editor and the database
     * @param curQuestion The question to edit in the list of questions made by the editor
     */
    public void editQuestion(Question curQuestion) {
        // This probably needs a UUID and several UI elements to work.
    }

    /**
     * Deletes a question from the list of questions made by the editor and the database
     * @param curQuestion The question to delete from the list of questions made by the editor
     */
    public void deleteQuestion(Question curQuestion) {
        this.questionsMade.remove(curQuestion);
        // Remove question from database
    }

    /**
     * Gets the list of questions made by the editor
     * @return The list of questions made by the editor
     */
    public ArrayList<Question> getQuestionsMade() {
        return this.questionsMade;
    }

    /**
     * Gets the role of the editor
     * @return The role of the editor
     */
    public String getRole() {
        return this.role;
    }
}
