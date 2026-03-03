package com.model;

import java.util.ArrayList;

/**
 * This class represents a comment on a question or another comment
 * @author Marshall Pigford
 */
public class Comment {
    private String title;
    private String comment;
    private ArrayList<Section> sections;
    private User author;
    private ArrayList<Comment> replies;
    private Double rating;
    // Could add numRatings for average rating system
    private Double numRatings;
    private ArrayList<CommentTag> tags;
    private boolean isQuestionAuthor;

    /**
     * Parameterized constructor for the Comment class
     * @param title The title of the comment
     * @param comment The content of the comment
     * @param author The author of the comment
     * @param ct The tags associated with the comment
     * @param sections The sections associated with the comment (if any)
     */
    public Comment(String title, String comment, User author, ArrayList<CommentTag> ct, ArrayList<Section> sections, boolean qA) {
        this.title = title;
        this.comment = comment;
        this.author = author;
        this.tags = ct;
        this.sections = sections;
        this.replies = new ArrayList<>();
        this.rating = null;
        this.numRatings = 0.0;
        this.isQuestionAuthor = qA;
    }

    /**
     * Adds a reply to the comment
     * @param comment The comment to add as a reply to this comment
     */
    public void addReply(Comment comment) {
        this.replies.add(comment);
    }

    /**
     * Rates the comment with the given rating
     * @param rating The rating to give the comment
     */
    public Double rateComment(Double rating) {
        // Cannot average ratings until we implement numRatings and average rating system, but we can still set the rating for now
        this.rating = rating * (1 / this.numRatings + 1) + this.rating * (this.numRatings / (this.numRatings + 1)); // Average rating system
        this.numRatings++;
        return this.rating;
    }

    /**
     * Edits the comment with the changes from the given comment
     * @param comment The comment containing the changes to make to this comment
     */
    public void editComment(Comment comment) {
        this.title = comment.getTitle();
        this.comment = comment.getComment();
        this.sections = comment.getSections();
        this.tags = comment.getTags();
    }

    /**
     * Checks if the author of the comment is the author of the question
     * @return True if the author of the comment is the author of the question, false otherwise
     */
    public boolean isAuthor() {
        return this.isQuestionAuthor;
    }

    /**
     * Adds a section to the comment
     * @param section The section to add to the comment
     */
    public void addSection(Section section) {
        this.sections.add(section);
    }

    /**
     * Adds a tag to the comment
     * @param tag The tag to add to the comment
     */
    public void addTag(CommentTag tag) {
        this.tags.add(tag);
    }

    /**
     * Gets the title of the comment
     * @return The title of the comment
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the content of the comment
     * @return The content of the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Gets the author of the comment
     * @return The author of the comment
     */
    public User getAuthor() {
        return this.author;
    }

    /**
     * Gets the tags associated with the comment
     * @return The tags associated with the comment
     */
    public ArrayList<CommentTag> getTags() {
        return this.tags;
    }

    /**
     * Gets the sections associated with the comment
     * @return The sections associated with the comment
     */
    public ArrayList<Section> getSections() {
        return this.sections;
    }

    /**
     * Gets the replies to the comment
     * @return The replies to the comment
     */
    public ArrayList<Comment> getReplies() {
        return this.replies;
    }

    /**
     * Gets the rating of the comment
     * @return The rating of the comment
     */
    public Double getRating() {
        return this.rating;
    }

    /**
     * Gets the number of ratings the comment has received
     * @return The number of ratings the comment has received
     */
    public Double getNumRatings() {
        return this.numRatings;
    }

    /**
     * Sets the title of the comment
     * @param title The new title to set for the comment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the content of the comment
     * @param comment The new content to set for the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the sections associated with the comment
     * @param sections The new sections to set for the comment
     */
    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    /**
     * Sets the tags associated with the comment
     * @param tags The new tags to set for the comment
     */
    public void setTags(ArrayList<CommentTag> tags) {
        this.tags = tags;
    }

    /**
     * Sets whether the author of the comment is the author of the question
     * @param isQuestionAuthor True if the author of the comment is the author of the question, false otherwise
     */
    public void setIsQuestionAuthor(boolean isQuestionAuthor) {
        this.isQuestionAuthor = isQuestionAuthor;
    }

}
