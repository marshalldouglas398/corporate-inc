package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Admin;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @FXML
    private User currentUser;

    //needs setuser to check if user is admin or editor and then switch to respective dashboard
    @FXML
    private void back(ActionEvent event) throws IOException { 
        if (currentUser instanceof Admin) {
            App.setRoot("dashA");
        } else {
            App.setRoot("dashE");
        }
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

}
