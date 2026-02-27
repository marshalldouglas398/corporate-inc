package com.model;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
    private static QuestionList questionList = QuestionList.getInstance();
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
                        Student student = new Student(username, password, dateOfBirth, email, uscID, major);
                        users.add(student);
                        break;
                    case "Editor":
                        JSONArray questionsArray = (JSONArray) userData.get(USER_QUESTIONS_MADE);
                        ArrayList<UUID> questionsMade = new ArrayList<>();
                        for (Object obj : questionsArray) {
                            questionsMade.add(UUID.fromString(obj.toString()));
                        }
                        Editor editor = new Editor(username, password, dateOfBirth, email, role);
                        for (UUID questionID : questionsMade) {
                            Question question = questionList.getQuestion(questionID);
                            if (question != null) {
                                editor.addQuestion(question);
                            }
                        }
                        users.add(editor);
                        break;
                    case "Admin":
                        Admin admin = new Admin(username, password, dateOfBirth, email);
                        users.add(admin);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid role: " + role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }
}
