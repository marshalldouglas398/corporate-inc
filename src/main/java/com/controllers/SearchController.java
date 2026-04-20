package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Admin;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.Question;
import com.model.Student;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private InterviewApplication app;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }
    public void setUser(User user) {
        this.currentUser = user;
        if(currentUser.getRole().equals("Student")) {
            this.student = (Student) user;
        } else if(currentUser.getRole().equals("Editor")) {
            this.editor = (Editor) user;
        } else if(currentUser.getRole().equals("Admin")) {
            this.admin = (Admin) user;
        }
    }
    @FXML
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    private void goToDash(ActionEvent event) throws IOException {
        FXMLLoader loader = null;
        if(admin != null) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        } else if(editor != null) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
        } else if(student != null) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dash.fxml"));
        }
        Parent root = loader.load();
        if(student != null) {
            DashController dash = loader.getController();
            dash.setUser(currentUser);
            dash.setInterviewApplication(app);
        } else if(admin != null) {
            DashAController dashA = loader.getController();
            dashA.setUser(currentUser);
            dashA.setInterviewApplication(app);
        } else if(editor != null) {
            DashEController dashE = loader.getController();
           dashE.setUser(currentUser);
           dashE.setInterviewApplication(app);
        }
        App.setRoot(root);
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        //App.setRoot("search");
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/settings.fxml"));
        Parent root = loader.load();
        SettingsController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
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
