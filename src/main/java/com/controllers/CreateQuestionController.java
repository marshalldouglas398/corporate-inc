package com.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.corporate.App;
import com.model.Admin;
import com.model.Course;
import com.model.Difficulty;
import com.model.Discipline;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.Question;
import com.model.QuestionList;
import com.model.QuestionTag;
import com.model.QuestionType;
import com.model.Section;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateQuestionController {
    
    @FXML
    private Button backButton;

    @FXML
    private TextField questionTitleField;

    @FXML
    private TextField questionDifficultyField;

    @FXML
    private TextField questionTypeField;

    @FXML
    private TextField questionTagsField;

    @FXML
    private TextField questionDisciplinesField;

    @FXML
    private TextField questionCoursesField;

    @FXML
    private CheckBox interviewModeCheckBox;

    @FXML
    private TextField interviewTimeField;

    @FXML
    private TextArea questionDescriptionArea;

    @FXML
    private TextField questionHintOneField;

    @FXML
    private TextField questionHintTwoField;

    @FXML
    private TextField questionHintThreeField;

    @FXML
    private Button addSectionButton;

    @FXML
    private TextField sectionOneTitleField;
    
    @FXML
    private TextArea sectionOneDescriptionArea;

    @FXML
    private TextField sectionOneFileField;

    @FXML
    private TextArea sectionOneCodeArea;

    @FXML
    private TextField sectionTwoTitleField;

    @FXML
    private TextArea sectionTwoDescriptionArea;

    @FXML
    private TextField sectionTwoFileField;

    @FXML
    private TextArea sectionTwoCodeArea;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    private User currentUser;
    private ArrayList<Section> sections = new ArrayList<>();
    private Editor editor;
    private Admin admin;
    private InterviewApplication app;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    public void setUser(User user) {
        this.currentUser = user;
        if(user instanceof Editor) {
            this.editor = (Editor) user;
        } else if(user instanceof Admin) {
            this.admin = (Admin) user;
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException { 
        String role = currentUser.getRole();
        String fxmlFile = "";
        FXMLLoader loader = null;
        if (role.equals("Admin")) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        } else if(role.equals("Editor")) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
        } else {
            System.out.println("Error: User role is not Admin or Editor");
        }
        Parent root = loader.load();
        if(role.equals("Admin")) {
            DashAController dashA = loader.getController();
            dashA.setUser(currentUser);
        }
        if(role.equals("Editor")) {
            DashEController dashE = loader.getController();
            dashE.setUser(currentUser);
        }
        App.setRoot(root);
    }
    @FXML
    private void addSection(ActionEvent event) {
        String title = sectionOneTitleField.getText();
        String description = sectionOneDescriptionArea.getText();
        File file = new File(sectionOneFileField.getText());
        String code = sectionOneCodeArea.getText();
        Section sectionOne = new Section(title, description, file, code);
        sections.add(sectionOne);
        sectionOneTitleField.clear();
        sectionOneDescriptionArea.clear();
        sectionOneFileField.clear();
        sectionOneCodeArea.clear();
    }
    @FXML
    private void saveQuestion(ActionEvent event) {
        String title = questionTitleField.getText();
        QuestionType type = safeEnum(QuestionType.class, questionTypeField.getText());
        Difficulty difficulty = safeEnum(Difficulty.class, questionDifficultyField.getText());
        Discipline disc = safeEnum(Discipline.class, questionDisciplinesField.getText());
        Course course = safeEnum(Course.class, questionCoursesField.getText());
        QuestionTag tag = safeEnum(QuestionTag.class, questionTagsField.getText());
       // QuestionType type = QuestionType.valueOf(questionTypeField.getText().toUpperCase());
       // Discipline disc = Discipline.valueOf(questionDisciplinesField.getText().toUpperCase());
        //Difficulty difficulty = Difficulty.valueOf(questionDifficultyField.getText().toUpperCase());
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(disc);
       // QuestionTag tag = QuestionTag.valueOf(questionTagsField.getText().toUpperCase());
        ArrayList<QuestionTag> tags = new ArrayList<>();
        tags.add(tag);
        //Course course = Course.valueOf(questionCoursesField.getText().toUpperCase());
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course);
        String timeLimit = interviewTimeField.getText();
        Boolean interviewMode = interviewModeCheckBox.isSelected();
        String description = questionDescriptionArea.getText();
        ArrayList<String> hints = new ArrayList<>();
        if(!questionHintOneField.getText().isEmpty()) {
            hints.add(questionHintOneField.getText());
        }
        if(!questionHintTwoField.getText().isEmpty()) {
            hints.add(questionHintTwoField.getText());
        }
        if(!questionHintThreeField.getText().isEmpty()) {
            hints.add(questionHintThreeField.getText());
        }
        QuestionList questionList = QuestionList.getInstance();
        app.addQuestion(title, currentUser, hints, type, disciplines, difficulty, courses, description);
        app.saveQuestions();
        Question current = questionList.getQuestion(title);
        if(!sections.isEmpty()) {
            for(Section section : sections) {
                current.addSection(section);
            }
        }
        current.setInterviewMode(interviewMode, Integer.parseInt(timeLimit));
        if(editor != null) {
            editor.addQuestion(current);
        } else if(admin != null) {
            admin.addQuestion(current);
        }
        app.saveQuestions();
        app.saveUsers();
    }
    private <T extends Enum<T>> T safeEnum(Class<T> enumClass, String value) {
        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

}
