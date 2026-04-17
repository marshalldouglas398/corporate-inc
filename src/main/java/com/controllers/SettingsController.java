package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Admin;
import com.model.Editor;
import com.model.Student;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SettingsController {
    @FXML
    private Button h_btn;
    
    @FXML
    private Button search_btn;

    @FXML
    private Button btn_logout;

    @FXML
    private Button stg_btn;
    
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
    private void goToDash(ActionEvent event) throws IOException {
        App.setRoot("dash");
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        App.setRoot("search");
    }

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }
}
