package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Admin;
import com.model.Editor;
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
    private Editor editor;
    private Admin admin;

    public void setUser(User user) {
        this.currentUser = user;
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

}
