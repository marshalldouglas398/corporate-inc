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
                file.write(jsonUsers.toJSONString());
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
        userDetails.put(USER_ID, user.getID().toString());
        userDetails.put(USER_NAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_DATE_OF_BIRTH, user.getBirthDate());
        userDetails.put(USER_EMAIL, user.getEmail());
        String role = user.getRole().toString();
        switch (role) {
            case "Student" :
                userDetails.put(USER_QUESTIONS_SOLVED, user.getQuestionsSolved());
                userDetails.put(USER_COURSES_TAKEN, user.getCoursesTaken());
                userDetails.put(USER_USCID, user.getUSCID());
                userDetails.put(USER_MAJOR, user.getMajor());
                break;
            case "Editor" :
                userDetails.put(USER_QUESTIONS_MADE, user.getQuestionsMade());
                break;
            case "Admin" :
                userDetails.put(USER_QUESTIONS_MADE, user.getQuestionsMade());
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
                file.write(jsonQuestions.toJSONString());
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
        questionDetails.put(QUESTION_SECTIONS, question.getSections());
        questionDetails.put(QUESTION_ID, question.getId());
        questionDetails.put(QUESTION_AUTHOR, question.getAuthor());
        questionDetails.put(QUESTION_COMMENTS, question.getComments());
        questionDetails.put(QUESTION_RATING, question.getRating());
        questionDetails.put(QUESTION_TYPE, question.getType());
        questionDetails.put(QUESTION_DISCIPLINE, question.getDiscipline());
        questionDetails.put(QUESTION_DIFFICULTY, question.getDifficulty());
        questionDetails.put(QUESTION_COURSES, question.getCourse());
        questionDetails.put(QUESTION_INTERVIEW, question.isInterviewMode());
        questionDetails.put(QUESTION_TAGS, question.getTag());
        questionDetails.put(QUESTION_HINTS, question.getHints());
    
        return questionDetails;
    }


    public static void main(String[] args){
        DataWriter.saveUsers();
        DataWriter.saveQuestions();
    }
}