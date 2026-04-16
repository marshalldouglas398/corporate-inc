package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Admin;
import com.model.Difficulty;
import com.model.Editor;
import com.model.Question;
import com.model.QuestionType;
import com.model.Student;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
// labels need to be switched to text box; design currently only works for the question it's designed for
public class QuestionController { // haven't implemented comments yet; just enough for dash
    @FXML
    private Button h_btn;
    
    @FXML
    private Button srch_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button stg_btn;

    @FXML
    private Button add_sec_btn;

    @FXML
    private Button add_t_btn;

    @FXML
    private Button sub_com_btn;

    @FXML
    private Button rep_com_btn;

    @FXML
    private Button rate_com_btn;

    @FXML
    private Label questionTitleLabel;

    @FXML
    private Label qTypeLabel;

    @FXML
    private Label qTagLabel;

    @FXML
    private Label difficultyLabel;

    @FXML
    private Label q_desc_label;


    private Question currentQuestion;
    private Student student;
    private Editor editor;
    private Admin admin;


    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException { // needs to be fixed; only for student rn
       if(student != null) {
            App.setRoot("dash");
        } else if(editor != null) {
            App.setRoot("dashE");
        } else if(admin != null) {
            App.setRoot("dashA");
        }
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        App.setRoot("settings");
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        App.setRoot("search");
    }

    public void setQuestion(Question question) {
        this.currentQuestion = question;
        questionTitleLabel.setText(question.getTitle());
        q_desc_label.setText(question.getDescription());
        setDiff(question.getDifficulty());
        setType(question.getType());
        //need to do tag but that is an array and i'm lazy rn
    }

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
    public void setType(QuestionType t) {
        String type = t.toString();
        String noenumtype = type.substring(type.indexOf('.') + 1);
        qTypeLabel.setText(noenumtype);
    }
    @FXML
    public void setDiff(Difficulty d) {
        String diff = d.toString();
        String noenumdiff = diff.substring(diff.indexOf('.') + 1);
        difficultyLabel.setText(noenumdiff);
    }
}
