package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A class representing a user of the software
 * @author Marshall Pigford
 */
public class User {
    protected String username;
    protected String password;
    protected Date dateOfBirth;
    protected UUID id;
    protected String email;
    protected String role;

    /**
     * Parameterized constructor for the User class
     * @param username The username of the user
     * @param password The password of the user
     * @param dateOfBirth The date of birth of the user
     * @param email The email of the user
     * @param role The role of the user
     */
    public User(String username, String password, Date dateOfBirth, String email, String role) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.id = UUID.randomUUID();
        this.email = email;
        this.role = role;
    }

    /**
     * Copy constructor for the User class
     * @param id The UUID of the user to copy
     * @param username The username of the user to copy
     * @param password The password of the user to copy
     * @param dateOfBirth The date of birth of the user to copy
     * @param email The email of the user to copy
     * @param role The role of the user to copy
     */
    public User(UUID id, String username, String password, Date dateOfBirth, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.role = role;
    }

    /**
     * Gets the username of the user
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    public String getID() {
        return id.toString();
    }

    public String getBirthDate() {
        return dateOfBirth.toString();
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return this.role;
    }

    public QuestionList getQuestionsSolved() {
        return null;
    }

    public ArrayList<Course> getCoursesTaken() {
        return null;
    }
    
    public String getUSCID() {
        return null;
    }

    public String getMajor() {
        return null;
    }

    public ArrayList<Question> getQuestionsMade() {
        return null;
    }

    /**
     * Resets the password of the user
     * @param password The new password to set for the user
     */
    public void resetPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user is an editor
     * @return true if the user is an editor, false otherwise
     */
    public boolean isEditor() { return false; } // This method will be overridden in the Editor class

    /**
     * Checks if the user is an admin
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin() { return false; } // This method will be overridden in the Admin class
}
