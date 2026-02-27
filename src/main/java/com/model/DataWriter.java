package com.model;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants{
    public static boolean saveUsers() {
        UserList users = UserList.getInstance();
        ArrayList<User> userList = users.getUsers();

        JSONArray jsonUsers = new JSONArray();

        for (int i = 0; i < userList.size(); i++) {
            jsonUsers.add(getUserJSON(userList.get(i)));
        }

        try (FileWriter file = new FileWriter(User_TEMP_FILE_NAME)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        
        return true;
    }

    public static JSONObject getUserJSON(User user) {
        JSONObject userDetails = newJSONObject();
        userDetails.put(USER_ID, user.getId().toString());
        userDetails.put(USER_NAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_DATE_OF_BIRTH, user.getBirthDate());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_QUESTIONS_SOLVED, user.getQuestionsSolved);
        userDetails.put(USER_COURSES_TAKEN, user.getCoursesTaken);
        userDetails.put(USER_USCID, user.getUSCID());
        userDetails.put(USER_MAJOR, user.getMajor());
        userDetails.put(USER_QUESTIONS_MADE, user.getQuestionsMade());
        userDetails.put(USER_ROLE, user.getRole());
    
        return userDetails;
    }

    public static boolean saveQuestions() {
        return true;
    }
    
    public static JSONObject getQuestionsJSON(Question question){
        JSONObject questionDetails = newJSONObject();
        questionDetails.put(QUESTION_TITLE, question.getQuestionTitle());
        questionDetails.put(QUESTION_DESCRIPTION, question.getDescription());
        questionDetails.put(QUESTION_SECTIONS, question.getSections());
        questionDetails.put(QUESTION_ID, question.getID());
        questionDetails.put(QUESTION_AUTHOR, question.getAuthor());
        questionDetails.put(QUESTION_COMMENTS, question.getComments());
        questionDetails.put(QUESTION_RATING, question.getRating());
        questionDetails.put(QUESTION_TYPE, question.getType());
        questionDetails.put(QUESTION_DISCIPLINE, question.getDiscipline());
        questionDetails.put(QUESTION_DIFFICULTY, question.getDifficulty());
        questionDetails.put(QUESTION_COURSES, question.getCourses());
        questionDetails.put(QUESTION_INTERVIEW, question.getInterview());
        questionDetails.put(QUESTION_TAGS, question.getTags());
        questionDetails.put(QUESTION_HINTS, question.getHints());
    }
}

public static void main(String[] args){
    DataWriter.saveUsers();
    DataWriter.saveQuestions();
}