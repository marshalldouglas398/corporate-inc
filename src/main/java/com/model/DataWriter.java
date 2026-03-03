package com.model;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants{
    public static boolean saveUsers() {
        UserList users = UserList.getInstance();
        ArrayList<User> userList = users.getUsers();
        JSONArray jsonUsers = new JSONArray();

        for(int i = 0; i < userList.size(); i++) {
            jsonUsers.add(getUserJSON(userList.get(i)));
        }

        try (FileWriter file = new FileWriter(USER_FILE_NAME)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_ID, user.getId().toString());
        userDetails.put(USER_NAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_DATE_OF_BIRTH, user.getDateOfBirth().toString());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_ROLE, user.getRole());

        if(user.isEditor()) {
            userDetails.put(USER_QUESTIONS_MADE, ((Editor) user).getQuestionsMade());
        } else if (user.isAdmin()) {
            userDetails.put(USER_QUESTIONS_MADE, ((Admin) user).getQuestionsMade());
        } else {
            userDetails.put(USER_QUESTIONS_SOLVED, ((Student) user).getQuestionsAnswered());
            userDetails.put(USER_COURSES_TAKEN, ((Student) user).getCoursesTaken());
            userDetails.put(USER_USCID, ((Student) user).getUscID());
            userDetails.put(USER_MAJOR, ((Student) user).getMajor());
        }
        
        return userDetails;
    }

    public static boolean saveQuestions() {
        QuestionList questions = QuestionList.getInstance();
        ArrayList<Question> questionList = questions.getQuestions();
        JSONArray jsonQuestions = new JSONArray();

        for(int i = 0; i < questionList.size(); i++) {
            jsonQuestions.add(getQuestionJSON(questionList.get(i)));
        }

        try (FileWriter file = new FileWriter(QUESTION_FILE_NAME)) {
            file.write(jsonQuestions.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static JSONObject getQuestionJSON(Question question) {
        JSONObject questionDetails = new JSONObject();
        questionDetails.put(QUESTION_ID, question.getId().toString());
        questionDetails.put(QUESTION_TITLE, question.getTitle());
        questionDetails.put(QUESTION_DESCRIPTION, question.getDescription());
        questionDetails.put(QUESTION_SECTIONS, question.getSections());
        questionDetails.put(QUESTION_AUTHOR, question.getAuthor().getUsername());
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
}