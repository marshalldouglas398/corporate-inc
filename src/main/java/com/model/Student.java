package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * This class represents a student, which is a type of user that can answer questions
 * @author Marshall Pigford
 */
public class Student extends User {
    private ArrayList<UUID> questionsAnswered;
    private ArrayList<Course> coursesTaken;
    private String uscID;
    private String major;

    /**
     * Parameterized constructor for the Student class
     * @param username The username of the student
     * @param password The password of the student
     * @param dateOfBirth The date of birth of the student
     * @param email The email of the student
     * @param uscID The USC ID of the student
     * @param major The major of the student
     */
    public Student(String username, String password, Date dateOfBirth, String email, String uscID, String major) {
        super(username, password, dateOfBirth, email, "Student");
        this.uscID = uscID;
        this.major = major;
        this.questionsAnswered = new ArrayList<>();
        this.coursesTaken = new ArrayList<>();
    }

    /**
     * Copy constructor for the Student class
     * @param id The UUID of the student to copy
     * @param username The username of the student to copy
     * @param password The password of the student to copy
     * @param dateOfBirth The date of birth of the student to copy
     * @param email The email of the student to copy
     * @param uscID The USC ID of the student to copy
     * @param major The major of the student to copy
     */
    public Student(UUID id, String username, String password, Date dateOfBirth, String email, String uscID, String major, 
                    ArrayList<UUID> questionsAnswered, ArrayList<Course> coursesTaken) {
        super(id, username, password, dateOfBirth, email, "Student");
        this.uscID = uscID;
        this.major = major;
        this.questionsAnswered = questionsAnswered;
        this.coursesTaken = coursesTaken;
    }

    /**
     * Converts the student to an editor
     * @return The editor instance
     */
    public Editor toEditor() {
        Editor editor = new Editor(username, password, dateOfBirth, email, role);
        editor.getQuestionsMade().addAll(this.getQuestionsAnswered());
        return editor;
    }

    /**
     * Gets the list of questions answered by the student
     * @return The list of questions answered by the student
     */
    public ArrayList<UUID> getQuestionsAnswered() {
        return this.questionsAnswered;
    }

    /**
     * Gets the list of courses taken by the student
     * @return The list of courses taken by the student
     */
    public ArrayList<Course> getCoursesTaken() {
        return this.coursesTaken;
    }

    /**
     * Gets the USC ID of the student
     * @return The USC ID of the student
     */
    public String getUSCID() {
        return this.uscID;
    }

    /**
     * Gets the major of the student
     * @return The major of the student
     */
    public String getMajor() {
        return this.major;
    }

    /**
     * Sets the USC ID of the student
     * @param uscID The new USC ID to set for the student
     */
    public void setUscID(String uscID) {
        this.uscID = uscID;
    }

    /**
     * Sets the major of the student
     * @param major The new major to set for the student
     */
    public void setMajor(String major) {
        this.major = major;
    }
}
