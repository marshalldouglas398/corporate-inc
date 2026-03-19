package com.model;

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
    protected int streak = 0;
    protected Date lastQuestionDate = new Date(0);

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
     * Copy constructor for the User class
     * @param user The user to copy
     */
    public User(User user) {
        this.id = user.getID();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.dateOfBirth = new Date(user.dateOfBirth.getTime());
        this.email = user.getEmail();
        this.role = user.getRole();
        this.streak = user.getStreak();
        this.lastQuestionDate = new Date(user.getLastQuestionDate().getTime());
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

    /**
     * Gets the UUID of the user
     * @return The UUID of the user
     */
    public UUID getID() {
        return id;
    }

    /**
     * Gets the date of birth of the user
     * @return The date of birth of the user in ISO 8601 format
     */
    public String getBirthDate() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(dateOfBirth);
    }

    /**
     * Gets the email of the user
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the role of the user
     * @return The role of the user
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Gets the current streak of the user
     * @return The current streak of the user
     */
    public int getStreak() {
        return this.streak;
    }

    /**
     * Gets the date of the last question answered by the user
     * @return The date of the last question answered by the user
     */
    public Date getLastQuestionDate() {
        return this.lastQuestionDate;
    }

    /**
     * Gets the list of questions solved by the user
     * @return The list of questions solved by the user
     */
    /*public QuestionList getQuestionsSolved() {
        Student student = (Student) this;
        return student.getQuestionsAnswered();
    }*/

    /**
     * Gets the list of courses taken by the user
     * @return The list of courses taken by the user
     */
    /*public ArrayList<Course> getCoursesTaken() {
        return null;
    }*/
    
    /**
     * Gets the USC ID of the user
     * @return The USC ID of the user
     */
    /*public String getUSCID() {
        return null;
    }

    /**
     * Gets the major of the user
     * @return The major of the user
     */
    /*public String getMajor() {
        return null;
    }*/

    /**
     * Gets the list of questions made by the user
     * @return The list of questions made by the user
     */
    /*public ArrayList<Question> getQuestionsMade() {
        return null;
    }*/

    /**
     * Increments the user's streak if they answer a question within 24 hours of their last question, otherwise resets the streak to 1
     */
    public void incrementStreak() {
        Date today = new Date();
        if (today.getTime() - lastQuestionDate.getTime() < 24 * 60 * 60 * 1000) {
            this.streak++;
        } else {
            this.streak = 1;
        }
        this.lastQuestionDate = today;
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
    public boolean isEditor() { 
        return this.role.equals("Editor");
    } // This method will be overridden in the Editor class

    /**
     * Checks if the user is an admin
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin() { 
        return this.role.equals("Admin"); 
    } // This method will be overridden in the Admin class
}
