package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Admin;
import com.model.Editor;
import com.model.Question;
import com.model.Student;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SearchController {
    @FXML
    private Button btn_questions;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_settings;

    @FXML
    private Button btn_logout;

    @FXML
    private TextField textfield_search_field;
    private User currentUser;
    private Student student;
    private Editor editor;
    private Admin admin;

    public void setUser(User user) {
        if(user instanceof Student) {
            this.student = (Student) user;
        } else if(user instanceof Editor) {
            this.editor = (Editor) user;
        } else if(user instanceof Admin) {
            this.admin = (Admin) user;
        }
    }
    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void goToDash(ActionEvent event) throws IOException {
        App.setRoot("dash");
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        App.setRoot("search");
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        App.setRoot("settings");
    }

    //
    private Question qSearch;
    Text qTitle;
    Text qDesc;
    Text qTags;
    Button qStartBtn;
    AnchorPane qAnchorPane;

    public QuestionDialog(Question question) {
        super();
    }

}
