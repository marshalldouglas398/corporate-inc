package com.model;
/**
 * This class contains all the constant strings used for parsing and writing JSON data related to users and questions.
 * @author Eden Moore
 */
public abstract class DataConstants {
    protected static final String USER_FILE_NAME = "json/users.json";
    protected static final String QUESTION_FILE_NAME = "json/questions.json";

    protected static final String USER_ID = "id";
    protected static final String USER_NAME = "username";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_DATE_OF_BIRTH = "dateOfBirth";
    protected static final String USER_EMAIL = "email";
    protected static final String USER_QUESTIONS_SOLVED = "questionsSolved";
    protected static final String USER_COURSES_TAKEN = "coursesTaken";
    protected static final String USER_USCID = "uscID";
    protected static final String USER_MAJOR = "major";
    protected static final String USER_QUESTIONS_MADE = "questionsMade";
    protected static final String USER_ROLE = "role";

    protected static final String QUESTION_TITLE = "title";
    protected static final String QUESTION_DESCRIPTION = "description";
    protected static final String QUESTION_SECTIONS = "sections";
    protected static final String QUESTION_ID = "id";
    protected static final String QUESTION_AUTHOR = "author";
    protected static final String QUESTION_COMMENTS = "comments";
    protected static final String QUESTION_RATING = "rating";
    protected static final String QUESTION_TYPE = "type";
    protected static final String QUESTION_DISCIPLINE = "discipline";
    protected static final String QUESTION_DIFFICULTY = "difficulty";
    protected static final String QUESTION_COURSES = "course";
    protected static final String QUESTION_INTERVIEW = "isInterviewMode";
    protected static final String QUESTION_TAGS = "tags";
    protected static final String QUESTION_HINTS = "hints";

    protected static final String SECTION_TITLE = "titleS";
    protected static final String SECTION_DESCRIPTION = "descriptionS";
    protected static final String SECTION_FILE = "file";
    protected static final String SECTION_CODE = "code";


    protected static final String COMMENT_TITLE = "titleC";
    protected static final String COMMENT_COMMENT = "comment";
    protected static final String COMMENT_SECTIONS = "sectionsC";
    protected static final String COMMENT_AUTHOR = "authorC";
    protected static final String COMMENT_REPLIES = "replies";
    protected static final String COMMENT_RATING = "ratingC";
    protected static final String COMMENT_TAGS = "tagsC";
}
