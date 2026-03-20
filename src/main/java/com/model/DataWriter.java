package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {
    public static boolean saveUsers() { 
        try {
            UserList userList = UserList.getInstance();
            ArrayList<User> users = userList.getUsers();

            JSONArray jsonUsers = new JSONArray();

            for (int i = 0; i < users.size(); i++) {
                jsonUsers.add(getUserJSON(users.get(i)));
            }

            try (FileWriter file = new FileWriter(USER_FILE_NAME)) {
                file.write("[\n");
                for (int i = 0; i < users.size(); i++) {
                    JSONObject userJSON = getUserJSON(users.get(i));
                    file.write(" " + userJSON.toJSONString());
                    if (i < users.size() - 1) {
                        file.write(", \n");
                    }
                }
                file.write("\n]");
                file.flush();
            } catch (IOException e) {
                e.printStackTrace(); 
            }
            return true;  
        } catch (Exception e) {
            return false;
        }
    }

    public static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_NAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_DATE_OF_BIRTH, user.getBirthDate());
        userDetails.put(USER_ID, user.getID().toString());
        userDetails.put(USER_EMAIL, user.getEmail());
        String role = user.getRole().toString();
        userDetails.put(USER_ROLE, role);
        switch (role) {
            case "Student" :
                Student student = (Student) user;
                userDetails.put(USER_QUESTIONS_SOLVED, student.getQuestionsAnswered());
                userDetails.put(USER_COURSES_TAKEN, student.getCoursesTaken());
                userDetails.put(USER_USCID, student.getUSCID());
                userDetails.put(USER_MAJOR, student.getMajor());
                userDetails.put(USER_STREAK, (long) student.getStreak());
                break;
            case "Editor" :
                Editor editor = (Editor) user;
                userDetails.put(USER_QUESTIONS_MADE, editor.getQuestionsMade());
                break;
            case "Admin" :
                Admin admin = (Admin) user;
                userDetails.put(USER_QUESTIONS_MADE, admin.getQuestionsMade());
                break;              
        }    
        return userDetails;
    }

    public static boolean saveQuestions() {
        try {
            QuestionList questionList = QuestionList.getInstance();
            ArrayList<Question> questions = questionList.getQuestions();

            JSONArray jsonQuestions = new JSONArray();

            for (int i = 0; i < questions.size(); i++) {
                jsonQuestions.add(getQuestionsJSON(questions.get(i)));
            }

            try (FileWriter file = new FileWriter(QUESTION_FILE_NAME)) {
                file.write("[\n");
                for (int i = 0; i < questions.size(); i++) {
                    JSONObject questionsJSON = getQuestionsJSON(questions.get(i));
                    file.write(" " + questionsJSON.toJSONString());
                    if (i < questions.size() - 1) {
                        file.write(", \n");
                    }
                }
                file.write("\n]");
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return true;  
        } catch (Exception e) {
            return false;
        }
    }
    
    public static JSONObject getQuestionsJSON(Question question){
        JSONObject questionDetails = new JSONObject();
        questionDetails.put(QUESTION_TITLE, question.getTitle());
        questionDetails.put(QUESTION_DESCRIPTION, question.getDescription());
        ArrayList<Section> sections = question.getSections();
            JSONArray jsonSections = new JSONArray();
            for (int i = 0; i < sections.size(); i++) {
                jsonSections.add(getSectionsJSON(sections.get(i)));
            }
        questionDetails.put(QUESTION_SECTIONS, jsonSections);
        questionDetails.put(QUESTION_ID, question.getId().toString());
        questionDetails.put(QUESTION_AUTHOR, question.getAuthor() != null ? question.getAuthor().getID().toString() : null);
        ArrayList<Comment> comments = question.getComments();
            JSONArray jsonComments = new JSONArray();
            for (int i = 0; i < comments.size(); i++) {
                jsonComments.add(getCommentsJSON(comments.get(i)));
            }
        questionDetails.put(QUESTION_COMMENTS, jsonComments);
        questionDetails.put(QUESTION_RATING, question.getRating());
        questionDetails.put(QUESTION_TYPE, question.getType() != null ? question.getType().toString() : null);
        JSONArray jsonDisciplines = new JSONArray();
        for (Discipline d : question.getDiscipline()) { jsonDisciplines.add(d.toString()); }
        questionDetails.put(QUESTION_DISCIPLINE, jsonDisciplines);
        questionDetails.put(QUESTION_DIFFICULTY, question.getDifficulty() != null ? question.getDifficulty().toString() : null);
        JSONArray jsonCourses = new JSONArray();
        for (Course c : question.getCourse()) { jsonCourses.add(c.toString()); }
        questionDetails.put(QUESTION_COURSES, jsonCourses);
        questionDetails.put(QUESTION_INTERVIEW, question.isInterviewMode());
        JSONArray jsonTags = new JSONArray();
        for (QuestionTag t : question.getTag()) { jsonTags.add(t.toString()); }
        questionDetails.put(QUESTION_TAGS, jsonTags);
        questionDetails.put(QUESTION_HINTS, question.getHints());
    
        return questionDetails;
    }
    public static JSONObject getCommentsJSON(Comment comment) {
        if(comment == null) {
            return null;
        }
        JSONObject commentDetails = new JSONObject();
        commentDetails.put(COMMENT_TITLE, comment.getTitle());
        commentDetails.put(COMMENT_AUTHOR, comment.getAuthor() != null ? comment.getAuthor().getID().toString() : null);
        commentDetails.put(COMMENT_COMMENT, comment.getComment());
        commentDetails.put(COMMENT_RATING, comment.getRating());
        JSONArray jsonCommentTags = new JSONArray();
        for (CommentTag t : comment.getTags()) { jsonCommentTags.add(t.toString()); }
        commentDetails.put(COMMENT_TAGS, jsonCommentTags);
        ArrayList<Section> sections = comment.getSections();
            JSONArray jsonSections = new JSONArray();
            for (int i = 0; i < sections.size(); i++) {
                jsonSections.add(getSectionsJSON(sections.get(i)));
            }
        commentDetails.put(COMMENT_SECTIONS, jsonSections);
        ArrayList<Comment> replies = comment.getReplies();
        JSONArray jsonReplies = new JSONArray();
        for (int i = 0; i < replies.size(); i++) {
            jsonReplies.add(getCommentsJSON(replies.get(i)));
        }
        commentDetails.put(COMMENT_REPLIES, jsonReplies);
        return commentDetails;
    }
    public static JSONObject getSectionsJSON(Section section) {
        JSONObject sectionDetails = new JSONObject();
        sectionDetails.put(SECTION_TITLE, section.getTitle());
        sectionDetails.put(SECTION_DESCRIPTION, section.getDescription());
        sectionDetails.put(SECTION_FILE, section.getFile().toString());
        sectionDetails.put(SECTION_CODE, section.getCode());
        return sectionDetails;
    }

    public static void main(String[] args){
        DataWriter.saveUsers();
        DataWriter.saveQuestions();
    }
}