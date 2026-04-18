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
    private int streak;
    private boolean editorRequest;

    /**
     * Parameterized constructor for the Student class
     * @param username The username of the student
     * @param password The password of the student
     * @param dateOfBirth The date of birth of the student
     * @param email The email of the student
     * @param uscID The USC ID of the student
     * @param major The major of the student
     * @param editorRequest Whether the student has requested to become an editor
     */
    public Student(String username, String password, Date dateOfBirth, String email, String uscID, String major) {
        super(username, password, dateOfBirth, email, "Student");
        this.uscID = uscID;
        this.major = major;
        this.questionsAnswered = new ArrayList<>();
        this.coursesTaken = new ArrayList<>();
        this.streak = 0;
        this.editorRequest = false;
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
     * @param questionsAnswered The list of questions answered by the student to copy
     * @param coursesTaken The list of courses taken by the student to copy
     * @param streak The daily streak of the student to copy
     * @param editorRequest Whether the student to copy has requested to become an editor
     */
    public Student(UUID id, String username, String password, Date dateOfBirth, String email, String uscID, String major, 
                    ArrayList<UUID> questionsAnswered, ArrayList<Course> coursesTaken, int streak, boolean editorRequest) {
        super(id, username, password, dateOfBirth, email, "Student");
        this.uscID = uscID;
        this.major = major;
        this.questionsAnswered = questionsAnswered;
        this.coursesTaken = coursesTaken;
        this.editorRequest = editorRequest;
        this.streak = streak;

    }

    /**
     * Converts the student to an editor
     * @return The editor instance
     */
    public Editor toEditor() {
        Editor editor = new Editor(username, password, dateOfBirth, email, "Editor");
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
    /*
     * Gets whether the student has requested to become an editor
     * @return Whether the student has requested to become an editor
     */
    public boolean hasRequestedEditor() {
        return this.editorRequest;
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

    /**
     * Gets the current daily streak of the student
     * @return The number of consecutive days the student has been active
     */
    public int getStreak() {
        return this.streak;
    }

    /**
     * Sets the daily streak of the student
     * @param streak The streak value to set
     */
    public void setStreak(int streak) {
        this.streak = streak;
    }

    /**
     * Increments the daily streak by 1
     */
    public void incrementStreak() {
        this.streak++;
    }

    /**
     * Sets whether the student has requested to become an editor
     * @param editorRequest The value to set for the editor request status
     */
    public void setEditorRequest(boolean editorRequest) {
        this.editorRequest = editorRequest;
    }

    public void addCourse(Course course) {
        this.coursesTaken.add(course);
    }
}
