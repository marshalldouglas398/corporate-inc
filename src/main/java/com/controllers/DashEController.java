package com.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import com.corporate.App;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.Question;
import com.model.QuestionList;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DashEController {
    @FXML
    private Button btn_logout;
    
    @FXML
    private Text welcomeMessage;

    @FXML
    private Button h_btn;

    @FXML
    private Button stg_btn;

    @FXML
    private Button btn_search;

    @FXML
    private Text date_text;

    @FXML
    private Button qtns_m_btn;

    @FXML
    private Text num_q_text;
    
    @FXML
    private Text avg_rate_txt;

    @FXML
    private Button add_q_btn;

    @FXML
    private Text rct_1_title;

    @FXML
    private Text rct_1_type;

    @FXML
    private Text rct_1_diff;

    @FXML
    private Rectangle diff_rect_1;

    @FXML
    private Text rct_2_title;

    @FXML
    private Text rct_2_type;

    @FXML
    private Text rct_2_diff;
    
    @FXML
    private Rectangle diff_rect_2;

    private Editor currentUser;
    private InterviewApplication app;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    public void setUser(User user) {
        this.currentUser = (Editor)user;
        displayWelcome(user.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM, d, yyyy", Locale.ENGLISH);
        String formattedDate = LocalDate.now().format(formatter);
        setDate(formattedDate);
        int num_q = currentUser.getQuestionsMade().size() - 1;
        setNumQ(num_q);
        ArrayList<UUID> questionsMade = currentUser.getQuestionsMade();
        if (questionsMade.size()-1 > 0) {
            avgQRating(questionsMade);
        } else {
            avg_rate_txt.setText("None");
        }
         int recent = currentUser.getQuestionsMade().size() - 1;
        if(recent == 1) {
            QuestionList ql = QuestionList.getInstance();
            Question q = ql.getQuestion(currentUser.getQuestionsMade().get(recent));
            rct_1_title.setText(q.getTitle());
            String type = q.getType().toString();
            String noenumtype = type.substring(type.indexOf('.') + 1);
            rct_1_type.setText(noenumtype);
            String diff = q.getDifficulty().toString();
            String noenumdiff = diff.substring(diff.indexOf('.') + 1);
            if(noenumdiff.equals("EASY")) {
                diff_rect_1.setStyle("-fx-fill: #AFFFAF");
                rct_1_diff.setStyle("-fx-fill: #487D48");
            } else if(noenumdiff.equals("MEDIUM")) {
                diff_rect_1.setStyle("-fx-fill: #ffc40066");
                rct_1_diff.setStyle("-fx-fill: #AD7032");
            } else if(noenumdiff.equals("HARD")) {
                diff_rect_1.setStyle("-fx-fill: #FF000066");
                rct_1_diff.setStyle("-fx-fill: #B41D2C");
            }
            rct_1_diff.setText(noenumdiff);
            rct_2_title.setVisible(false);
            rct_2_type.setVisible(false);
            rct_2_diff.setVisible(false);
            diff_rect_2.setVisible(false);
        } else if(recent > 1) {
            QuestionList ql = QuestionList.getInstance();
            Question q = ql.getQuestion(currentUser.getQuestionsMade().get(recent));
            Question q2 = ql.getQuestion(currentUser.getQuestionsMade().get(recent - 1));
            rct_1_title.setText(q.getTitle());
            String type = q.getType().toString();
            String noenumtype = type.substring(type.indexOf('.') + 1);
            rct_1_type.setText(noenumtype);
            String diff = q.getDifficulty().toString();
            String noenumdiff = diff.substring(diff.indexOf('.') + 1);
            if(noenumdiff.equals("EASY")) {
                diff_rect_1.setStyle("-fx-fill: #AFFFAF");
                rct_1_diff.setStyle("-fx-fill: #487D48");
            } else if(noenumdiff.equals("MEDIUM")) {
                diff_rect_1.setStyle("-fx-fill: #ffc40066");
                rct_1_diff.setStyle("-fx-fill: #AD7032");
            } else if(noenumdiff.equals("HARD")) {
                diff_rect_1.setStyle("-fx-fill: #FF000066");
                rct_1_diff.setStyle("-fx-fill: #B41D2C");
            }
            rct_1_diff.setText(noenumdiff);
            rct_2_title.setText(q2.getTitle());
            String type2 = q2.getType().toString();
            String noenumtype2 = type2.substring(type2.indexOf('.') + 1);
            rct_2_type.setText(noenumtype2);
            String diff2 = q2.getDifficulty().toString();
            String noenumdiff2 = diff2.substring(diff2.indexOf('.') + 1);
            if(noenumdiff2.equals("EASY")) {
                diff_rect_2.setStyle("-fx-fill: #AFFFAF");
                rct_2_diff.setStyle("-fx-fill: #487D48");
            } else if(noenumdiff2.equals("MEDIUM")) {
                diff_rect_2.setStyle("-fx-fill: #ffc40066");
                rct_2_diff.setStyle("-fx-fill: #AD7032");
            } else if(noenumdiff2.equals("HARD")) {
                diff_rect_2.setStyle("-fx-fill: #FF000066");
                rct_2_diff.setStyle("-fx-fill: #B41D2C");
            }
            rct_2_diff.setText(noenumdiff2);
        } else if(recent == 0) {
            rct_1_title.setVisible(false);
            rct_1_type.setVisible(false);
            rct_1_diff.setVisible(false);
            diff_rect_1.setVisible(false);
            rct_2_title.setVisible(false);
            rct_2_type.setVisible(false);
            rct_2_diff.setVisible(false);
            diff_rect_2.setVisible(false);
        }
    }

    @FXML
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    public void displayWelcome(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");

    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setUser(currentUser);
        App.setRoot(root);
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

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
       //already here
    }

    @FXML
    private void viewQuestionsMade(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/editorQ.fxml"));
        Parent root = loader.load();
        EditorQController controller = loader.getController();
        controller.setUser(currentUser);
        App.setRoot(root);
    }

    @FXML
    private void addQuestion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/createQuestion.fxml"));
        Parent root = loader.load();
        CreateQuestionController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    @FXML
    public void setDate(String date) {
        date_text.setText(date);
    }

    @FXML
    public void setNumQ(int num_q) {
        num_q_text.setText(Integer.toString(num_q));
    }

    @FXML
    private void avgQRating(ArrayList<UUID> questionsMade) {
        double totalRating = 0;
        QuestionList qList = QuestionList.getInstance();
        for (UUID qID : questionsMade) {
            Question currentQ = qList.getQuestion(qID);
            totalRating += currentQ.getRating();
        }
        double averageRating = totalRating / questionsMade.size();
        avg_rate_txt.setText(String.format("%.2f", averageRating));
    }

}
