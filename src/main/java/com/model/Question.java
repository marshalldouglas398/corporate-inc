package com.model;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This class represents a question posted by a user
 * @author Marshall Pigford
 */
public class Question {
    private String title;
    private String description;
    private ArrayList<Section> sections;
    private UUID id; 
    private User author;
    private ArrayList<Comment> comments;
    private Double rating;
    private Double numRatings; // Same numRatings solution as Comment class, we can change it later if we want to
    private QuestionType type;
    private ArrayList<Discipline> discipline;
    private Difficulty difficulty;
    private ArrayList<Course> course;
    private boolean isInterviewMode; // I'll make a getter and setter for this
    private ArrayList<QuestionTag> tag;
    private ArrayList<String> hints;

    /**
     * Parameterized constructor for the Question class
     * @param title The title of the question
     * @param author The author of the question
     * @param hints The hints for the question
     * @param type The type of the question
     * @param discipline The discipline(s) the question belongs to
     * @param difficulty The difficulty of the question
     * @param course The course(s) the question belongs to
     */
    public Question(String title, User author, ArrayList<String> hints, QuestionType type, ArrayList<Discipline> discipline, Difficulty difficulty, ArrayList<Course> course) {
        this.title = title;
        this.author = author;
        this.hints = hints;
        this.type = type;
        this.discipline = discipline;
        this.difficulty = difficulty;
        this.course = course;
        this.id = UUID.randomUUID();
        this.comments = new ArrayList<>();
        this.rating = null;
        this.numRatings = 0.0;
    }

    /**
     * Copy constructor for the Question class
     * @param id The UUID of the question to copy
     */
    public Question(UUID id) {
        // You need to implement question retrieval first before you can implement this constructor
    }

    /**
     * Constructor for a question with only a title
     * @param title The title of the question
     */
    public Question(String title) {
        this.title = title;
        this.author = null;
        this.hints = new ArrayList<>();
        this.type = null;
        this.discipline = new ArrayList<>();
        this.difficulty = null;
        this.course = new ArrayList<>();
        this.id = UUID.randomUUID();
        this.comments = new ArrayList<>();
        this.rating = null;
        this.numRatings = 0.0;
    }

    /**
     * Adds a comment to the question
     * @param comment The comment to get the title from
     */
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    /**
     * Changes the question to interview mode with the given time limit
     * @param b Whether to set the question to interview mode or not
     * @param time The time limit for the interview mode in minutes (we can implement the timer functionality later if we want to, but for now we can just set the interview mode to true or false)
     */
    public void setInterviewMode(boolean b, int time) {
        this.isInterviewMode = b;
        // We can implement the timer functionality later if we want to, but for now we can just set the interview mode to true or false
    }

    /**
     * Rates the question with the given rating
     * @param rating The rating to give the question
     */
    public Double addRating(Double rating) {
        this.rating = rating * (1 / this.numRatings + 1) + this.rating * (this.numRatings / (this.numRatings + 1)); // Average rating system
        this.numRatings++;
        return this.rating;
    }

    /**
     * Completes the question for the given student
     * @param student The student to complete the question for
     */
    public void completeQuestion(Student student) {
        student.getQuestionsAnswered().add(this);
    }

    /**
     * Adds a solution to the question within the given comment
     * @param comment The comment containing the solution to add to the question
     */
    public void submitSolution(Comment comment) {
        comment.addTag(CommentTag.SOLUTION);
        this.comments.add(comment);
    }

    /**
     * Adds a section to the question with the given parameters
     * @param title The title of the section
     * @param description The description of the section
     * @param file The file associated with the section (if any)
     * @param code The code associated with the section (if any)
     */
    public void addSection(String title, String description, File file, String code) {
        this.sections.add(new Section(title, description, file, code));
    }

    /**
     * Checks if the given user is the author of the question
     * @param user The user to check if they are the author of the question
     * @return True if the given user is the author of the question, false otherwise
     */
    public boolean isAuthor(Editor user) {
        return user.getQuestionsMade().contains(this);
    }

    /**
     * Checks if the given admin is the author of the question
     * @param user The admin to check if they are the author of the question
     * @return True if the given admin is the author of the question, false otherwise
     */
    public boolean isAuthor(Admin user) {
        return user.getQuestionsMade().contains(this);
    }

    /**
     * Gets the title of the question
     * @return The title of the question
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * Gets the description of the question
     * @return The description of the question
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the sections of the question
     * @return The sections of the question
     */
    public ArrayList<Section> getSections() {
        return this.sections;
    }

    /**
     * Gets the UUID of the question
     * @return The UUID of the question
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Gets the author of the question
     * @return The author of the question
     */
    public User getAuthor() {
        return this.author;
    }

    /**
     * Gets the comments on the question
     * @return The comments on the question
     */
    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    /**
     * Gets the rating of the question
     * @return The rating of the question
     */
    public Double getRating() {
        return this.rating;
    }

    /**
     * Gets the number of ratings the question has received
     * @return The number of ratings the question has received
     */
    public Double getNumRatings() {
        return this.numRatings;
    }

    /**
     * Gets the type of the question
     * @return The type of the question
     */
    public QuestionType getType() {
        return this.type;
    }

    /**
     * Gets the discipline(s) the question belongs to
     * @return The discipline(s) the question belongs to
     */
    public ArrayList<Discipline> getDiscipline() {
        return this.discipline;
    }

    /**
     * Gets the difficulty of the question
     * @return The difficulty of the question
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Gets the course(s) the question belongs to
     * @return The course(s) the question belongs to
     */
    public ArrayList<Course> getCourse() {
        return this.course;
    }

    /**
     * Checks if the question is in interview mode
     * @return True if the question is in interview mode, false otherwise
     */
    public boolean isInterviewMode() {
        return this.isInterviewMode;
    }

    /**
     * Gets the tags associated with the question
     * @return The tags associated with the question
     */
    public ArrayList<QuestionTag> getTag() {
        return this.tag;
    }

    /**
     * Gets the hints for the question
     * @return The hints for the question
     */
    public ArrayList<String> getHints() {
        return this.hints;
    }

    /**
     * Sets the title of the question
     * @param title The new title to set for the question
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the question
     * @param description The new description to set for the question
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the difficulty of the question
     * @param difficulty The new difficulty to set for the question
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

}
