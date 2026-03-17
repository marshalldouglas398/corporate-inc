package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * This class represents an editor, which is a type of user that can create, edit, and delete questions
 * @author Marshall Pigford
 */
public class Editor extends User {
    private ArrayList<UUID> questionsMade;
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
        super(UUID.randomUUID(), username, password, dateOfBirth, email, role);
        this.role = role;
        this.questionsMade = new ArrayList<>();
    }

    /**
     * Copy constructor for the Editor class
     * @param id The UUID of the editor to copy
     * @param username The username of the editor to copy
     * @param password The password of the editor to copy
     * @param dateOfBirth The date of birth of the editor to copy
     * @param email The email of the editor to copy
     * @param role The role of the editor to copy
     */
    public Editor(UUID id, String username, String password, Date dateOfBirth, String email, String role, ArrayList<UUID> questionsMade) {
        super(id, username, password, dateOfBirth, email, role);
        this.role = role;
        this.questionsMade = questionsMade;
    }

    /**
     * Adds a question to the list of questions made by the editor and the database
     * @param newQuestion The question to add to the list of questions made by the editor
     */
    public void addQuestion(Question newQuestion) {
        UUID questionID = newQuestion.getId();
        this.questionsMade.add(questionID);
        QuestionList questionList = QuestionList.getInstance();
        questionList.getQuestions().add(newQuestion);
        questionList.save();
    }
    
    /**
     * Edits a question in the list of questions made by the editor and the database
     * @param curQuestion The question to edit in the list of questions made by the editor
     */
    public void editQuestion(Question curQuestion, Question newQuestion) {
        UUID newQuestionID = newQuestion.getId();
        UUID curQuestionID = curQuestion.getId();
        this.questionsMade.remove(curQuestionID);
        this.questionsMade.add(newQuestionID);
        QuestionList questionList = QuestionList.getInstance();
        questionList.getQuestions().remove(curQuestion);
        questionList.getQuestions().add(newQuestion);
        questionList.save();
    }

    /**
     * Deletes a question from the list of questions made by the editor and the database
     * @param curQuestion The question to delete from the list of questions made by the editor
     */
    public void deleteQuestion(Question curQuestion) {
        UUID curQuestionID = curQuestion.getId();
        this.questionsMade.remove(curQuestionID);
        QuestionList questionList = QuestionList.getInstance();
        questionList.getQuestions().remove(curQuestion);
        questionList.save();
    }

    /**
     * Gets the list of questions made by the editor
     * @return The list of questions made by the editor
     */
    public ArrayList<UUID> getQuestionsMade() {
        return this.questionsMade;
    }

    /**
     * Gets the role of the editor
     * @return The role of the editor
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Checks if the user is an editor
     * @return true if the user is an editor, false otherwise
     */
    @Override
    public boolean isEditor() { return true; } // This method overrides the isEditor method in the User class to return true for editors
}
