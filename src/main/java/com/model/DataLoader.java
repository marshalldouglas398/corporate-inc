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
    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            FileReader reader = new FileReader(QUESTION_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray questionJSON = (JSONArray) parser.parse(reader);
            for (int i = 0; i < questionJSON.size(); i++) {
                JSONObject questionData = (JSONObject) questionJSON.get(i);
                UUID id = UUID.fromString(questionData.get(QUESTION_ID).toString());
                String title = questionData.get(QUESTION_TITLE).toString();
                User author = UserList.getInstance().getUser(UUID.fromString(questionData.get(QUESTION_AUTHOR).toString()));
                JSONArray hintsArray = (JSONArray) questionData.get(QUESTION_HINTS);
                ArrayList<String> hints = new ArrayList<>();
                for (Object obj : hintsArray) {
                    hints.add(obj.toString());
                }
                QuestionType type = QuestionType.valueOf(questionData.get(QUESTION_TYPE).toString());
                JSONArray disciplineArray = (JSONArray) questionData.get(QUESTION_DISCIPLINE);
                ArrayList<Discipline> discipline = new ArrayList<>();
                for (Object obj : disciplineArray) {
                    discipline.add(Discipline.valueOf(obj.toString()));
                }
                Difficulty difficulty = Difficulty.valueOf(questionData.get(QUESTION_DIFFICULTY).toString());
                JSONArray courseArray = (JSONArray) questionData.get(QUESTION_COURSES);
                ArrayList<Course> course = new ArrayList<>();
                for (Object obj : courseArray) {
                    course.add(Course.valueOf(obj.toString()));
                }
                JSONArray tagArray = (JSONArray) questionData.get(QUESTION_TAGS);
                ArrayList<QuestionTag> tag = new ArrayList<>();
                for (Object obj : tagArray) {
                    tag.add(QuestionTag.valueOf(obj.toString()));
                }
                Question question = new Question(title, author, hints, type, discipline, difficulty, course);
                questions.add(question);
                QuestionList.getInstance().addQuestion(title, author, hints, type, discipline, difficulty, course);
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
                        Student student = new Student(id, username, password, dateOfBirth, email, uscID, major);
                        users.add(student);
                        break;
                    case "Editor":
                        JSONArray questionsArray = (JSONArray) userData.get(USER_QUESTIONS_MADE);
                        ArrayList<UUID> questionsMade = new ArrayList<>();
                        for (Object obj : questionsArray) {
                            questionsMade.add(UUID.fromString(obj.toString()));     
                        }
                        Editor editor = new Editor(id, username, password, dateOfBirth, email, role);
                        for (UUID questionID : questionsMade) {
                            Question question = QuestionList.getInstance().getQuestion(questionID);
                            if (question != null) {
                                editor.addQuestion(question);
                            }
                        }
                        users.add(editor);
                        break;
                    case "Admin":
                        Admin admin = new Admin(id, username, password, dateOfBirth, email);
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
