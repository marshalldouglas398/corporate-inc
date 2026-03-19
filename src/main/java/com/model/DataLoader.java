package com.model;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
    /**
     * Parses a JSONArray of sections and returns an ArrayList of Section objects
     * @param sectionsArray The JSONArray of sections to parse
     * @return An ArrayList of Section objects
     */
    private static ArrayList<Section> parseSections(JSONArray sectionsArray) {
        ArrayList<Section> sections = new ArrayList<>();
        if (sectionsArray == null) {
            return sections;
        }

        for (Object obj : sectionsArray) {
            if (!(obj instanceof JSONObject)) {
                continue;
            }
            JSONObject sectionData = (JSONObject) obj;
            String title = sectionData.get(SECTION_TITLE) == null ? "" : sectionData.get(SECTION_TITLE).toString();
            String description = sectionData.get(SECTION_DESCRIPTION) == null ? "" : sectionData.get(SECTION_DESCRIPTION).toString();
            String fileStr = sectionData.get(SECTION_FILE) == null ? null : sectionData.get(SECTION_FILE).toString();
            String code = sectionData.get(SECTION_CODE) == null ? "" : sectionData.get(SECTION_CODE).toString();
            File file = (fileStr == null || fileStr.isEmpty()) ? null : new File(fileStr);
            sections.add(new Section(title, description, file, code));
        }
        return sections;
    }

    /**
     * Parses a JSONArray of comments and returns an ArrayList of Comment objects
     * @param commentsArray The JSONArray of comments to parse
     * @return An ArrayList of Comment objects
     */
    private static ArrayList<Comment> parseComments(JSONArray commentsArray) {
        ArrayList<Comment> comments = new ArrayList<>();
        if (commentsArray == null) {
            return comments;
        }

        for (Object obj : commentsArray) {
            if (!(obj instanceof JSONObject)) {
                continue;
            }
            JSONObject commentData = (JSONObject) obj;
            String title = commentData.get(COMMENT_TITLE) == null ? "" : commentData.get(COMMENT_TITLE).toString();
            String commentText = commentData.get(COMMENT_COMMENT) == null ? "" : commentData.get(COMMENT_COMMENT).toString();

            User author = null;
            Object authorObj = commentData.get(COMMENT_AUTHOR);
            if (authorObj != null) {
                author = UserList.getInstance().getUser(UUID.fromString(authorObj.toString()));
            }

            JSONArray sectionsArray = (JSONArray) commentData.get(COMMENT_SECTIONS);
            ArrayList<Section> sections = parseSections(sectionsArray);

            JSONArray tagsArray = (JSONArray) commentData.get(COMMENT_TAGS);
            ArrayList<CommentTag> tags = new ArrayList<>();
            if (tagsArray != null) {
                for (Object tagObj : tagsArray) {
                    if (tagObj != null) {
                        tags.add(CommentTag.valueOf(tagObj.toString()));
                    }
                }
            }

            JSONArray repliesArray = (JSONArray) commentData.get(COMMENT_REPLIES);
            ArrayList<Comment> replies = parseComments(repliesArray);

            Double rating = null;
            Object ratingObj = commentData.get(COMMENT_RATING);
            if (ratingObj instanceof Number) {
                rating = ((Number) ratingObj).doubleValue();
            } else if (ratingObj != null) {
                rating = Double.valueOf(ratingObj.toString());
            }
            Double numRatings = (rating == null) ? 0.0 : 1.0;
            boolean isQuestionAuthor = false;
            Object isQuestionAuthorObj = commentData.get(COMMENT_IS_QUESTION_AUTHOR);
            if (isQuestionAuthorObj instanceof Boolean) {
                isQuestionAuthor = (Boolean) isQuestionAuthorObj;
            } else if (isQuestionAuthorObj != null) {
                isQuestionAuthor = Boolean.parseBoolean(isQuestionAuthorObj.toString());
            }

            comments.add(new Comment(title, commentText, author, tags, sections, replies, rating, numRatings, isQuestionAuthor));
        }
        return comments;
    }

    /**
     * Gets the questions from the data file
     * @return An ArrayList of Question objects
     */
    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            FileReader reader = new FileReader(QUESTION_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray questionJSON = (JSONArray) parser.parse(reader);
            for (int i = 0; i < questionJSON.size(); i++) {
                JSONObject questionData = (JSONObject) questionJSON.get(i);

                UUID id = UUID.fromString(questionData.get(QUESTION_ID).toString());
                String title = questionData.get(QUESTION_TITLE) == null ? "" : questionData.get(QUESTION_TITLE).toString();
                String description = questionData.get(QUESTION_DESCRIPTION) == null ? "" : questionData.get(QUESTION_DESCRIPTION).toString();

                User author = null;
                Object authorObj = questionData.get(QUESTION_AUTHOR);
                if (authorObj != null) {
                    //author = UUID.fromString(authorObj.toString());
                    author = UserList.getInstance().getUser(UUID.fromString(authorObj.toString()));
                }

                JSONArray hintsArray = (JSONArray) questionData.get(QUESTION_HINTS);
                ArrayList<String> hints = new ArrayList<>();
                if (hintsArray != null) {
                    for (Object obj : hintsArray) {
                        hints.add(obj.toString());
                    }
                }

                QuestionType type = questionData.get(QUESTION_TYPE) == null ? null
                        : QuestionType.valueOf(questionData.get(QUESTION_TYPE).toString());

                JSONArray disciplineArray = (JSONArray) questionData.get(QUESTION_DISCIPLINE);
                ArrayList<Discipline> discipline = new ArrayList<>();
                if (disciplineArray != null) {
                    for (Object obj : disciplineArray) {
                        discipline.add(Discipline.valueOf(obj.toString()));
                    }
                }
                Difficulty difficulty = questionData.get(QUESTION_DIFFICULTY) == null ? null
                        : Difficulty.valueOf(questionData.get(QUESTION_DIFFICULTY).toString());

                JSONArray courseArray = (JSONArray) questionData.get(QUESTION_COURSES);
                ArrayList<Course> course = new ArrayList<>();
                if (courseArray != null) {
                    for (Object obj : courseArray) {
                        course.add(Course.valueOf(obj.toString()));
                    }
                }

                JSONArray tagArray = (JSONArray) questionData.get(QUESTION_TAGS);
                ArrayList<QuestionTag> tag = new ArrayList<>();
                if (tagArray != null) {
                    for (Object obj : tagArray) {
                        tag.add(QuestionTag.valueOf(obj.toString()));
                    }
                }

                JSONArray sectionsArray = (JSONArray) questionData.get(QUESTION_SECTIONS);
                ArrayList<Section> sections = parseSections(sectionsArray);

                JSONArray commentsArray = (JSONArray) questionData.get(QUESTION_COMMENTS);
                ArrayList<Comment> comments = parseComments(commentsArray);

                Double rating = null;
                Object ratingObj = questionData.get(QUESTION_RATING);
                if (ratingObj instanceof Number) {
                    rating = ((Number) ratingObj).doubleValue();
                } else if (ratingObj != null) {
                    rating = Double.valueOf(ratingObj.toString());
                }
                Double numRatings = (rating == null) ? 0.0 : 1.0;

                boolean isInterviewMode = false;
                Object isInterviewModeObj = questionData.get(QUESTION_INTERVIEW);
                if (isInterviewModeObj instanceof Boolean) {
                    isInterviewMode = (Boolean) isInterviewModeObj;
                } else if (isInterviewModeObj != null) {
                    isInterviewMode = Boolean.parseBoolean(isInterviewModeObj.toString());
                }

                Question question = new Question(id, title, description, sections, author, comments, rating, numRatings, type, discipline, difficulty, course,
                        isInterviewMode, tag, hints, -1);
                questions.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return questions;
    }

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        
        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray userJSON = (JSONArray) parser.parse(reader);

            for (int i = 0; i < userJSON.size(); i++) {
                JSONObject userData = (JSONObject) userJSON.get(i);
                UUID id = UUID.fromString(userData.get(USER_ID).toString());
                String username = userData.get(USER_NAME).toString();
                String password = userData.get(USER_PASSWORD).toString();
                Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(userData.get(USER_DATE_OF_BIRTH).toString());
                String email = userData.get(USER_EMAIL).toString();
                String role = userData.get(USER_ROLE).toString();
                switch (role) {
                    case "Student":
                        String uscID = userData.get(USER_USCID).toString();
                        String major = userData.get(USER_MAJOR).toString();
                        ArrayList<UUID> questionsAnswered = (ArrayList<UUID>) userData.get(USER_QUESTIONS_SOLVED);
                        ArrayList<Course> coursesTaken = (ArrayList<Course>) userData.get(USER_COURSES_TAKEN);
                        Student student = new Student(id, username, password, dateOfBirth, email, uscID, major, questionsAnswered, coursesTaken);
                        users.add(student);
                        break;
                    case "Editor":
                        /*JSONArray questionsArray = (JSONArray) userData.get(USER_QUESTIONS_MADE);
                        ArrayList<UUID> questionsMade = new ArrayList<>();
                        for (Object obj : questionsArray) {
                            questionsMade.add(UUID.fromString(obj.toString()));     
                        }*/
                        ArrayList<UUID> questionsMadeE = (ArrayList<UUID>) userData.get(USER_QUESTIONS_MADE);
                        Editor editor = new Editor(id, username, password, dateOfBirth, email, role, questionsMadeE);
                        /*for (UUID questionID : questionsMadeE) {
                            Question question = QuestionList.getInstance().getQuestion(questionID);
                            if (question != null) {
                                editor.addQuestion(question);
                            }
                        }*/
                        users.add(editor);
                        break;
                    case "Admin":
                        ArrayList<UUID> questionsMadeA = (ArrayList<UUID>) userData.get(USER_QUESTIONS_MADE);
                        Admin admin = new Admin(id, username, password, dateOfBirth, email, questionsMadeA);
                        users.add(admin);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid role: " + role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
