package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {
    public static boolean saveUsers() { 
        System.out.println("Absolute path: " + new java.io.File(USER_FILE_NAME).getAbsolutePath());
        System.out.println("Writable: " + new java.io.File(USER_FILE_NAME).canWrite());
        try {
            UserList userList = UserList.getInstance();
            System.out.println("UserList instance in saveUsers: " + userList);
            ArrayList<User> users = userList.getUsers();

            JSONArray jsonUsers = new JSONArray();

            for (int i = 0; i < users.size(); i++) {
                System.out.println("Saving user: " + users.get(i).getUsername());
                jsonUsers.add(getUserJSON(users.get(i)));
            }

            try (FileWriter file = new FileWriter(USER_FILE_NAME)) {
                System.out.println("Saving users count: " + users.size());
                file.write(jsonUsers.toJSONString());
                file.close();
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
        //userDetails.put(USER_DATE_OF_BIRTH, user.getBirthDate());
        //DateTimeFormatter formatter = new DateTimeFormatter;
        userDetails.put(USER_DATE_OF_BIRTH, DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).parse(user.getBirthDate()));
        userDetails.put(USER_ID, user.getID().toString());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_ROLE, user.getRole());
        String role = user.getRole();
        switch (role) {
            case "Student" :
                /*JSONArray solvedArray = new JSONArray();
                for (UUID id : user.getQuestionsSolved()) {
                    solvedArray.add(id.toString());
                }
                userDetails.put(USER_QUESTIONS_SOLVED, solvedArray);
                JSONArray courseArray = new JSONArray();
                for (Course course : user.getCoursesTaken()) {
                    courseArray.add(course.toString());
                }
                userDetails.put(USER_COURSES_TAKEN, courseArray);*/
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
        questionDetails.put(QUESTION_AUTHOR, question.getAuthor().getID().toString());
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