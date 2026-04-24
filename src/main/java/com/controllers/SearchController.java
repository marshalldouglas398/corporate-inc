package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import com.corporate.App;
import com.model.Admin;
import com.model.Difficulty;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.Question;
import com.model.QuestionList;
import com.model.QuestionType;
import com.model.Student;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SearchController {
    @FXML
    private TextField textfield_search_bottom;

    @FXML
    private HBox search_filter_row;

    @FXML
    private ComboBox<String> type_filter_box;

    @FXML
    private ComboBox<String> difficulty_filter_box;

    @FXML
    private VBox search_results_list;

    private User currentUser;
    private Student student;
    private Editor editor;
    private Admin admin;
    private InterviewApplication app;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    @FXML
    public void initialize() {
        type_filter_box.getItems().add("ALL TYPES");
        for (QuestionType type : QuestionType.values()) {
            type_filter_box.getItems().add(type.toString());
        }
        type_filter_box.setValue("ALL TYPES");

        difficulty_filter_box.getItems().add("ALL DIFFICULTIES");
        for (Difficulty difficulty : Difficulty.values()) {
            difficulty_filter_box.getItems().add(difficulty.toString());
        }
        difficulty_filter_box.setValue("ALL DIFFICULTIES");
        if(search_filter_row != null) {
            search_filter_row.setVisible(false);
            search_filter_row.setManaged(false);
        }
    }

    public void setUser(User user) {
        this.currentUser = user;
        this.student = null;
        this.editor = null;
        this.admin = null;
        if(this.app == null) {
            this.app = new InterviewApplication();
        }
        if(currentUser.getRole().equals("Student")) {
            this.student = (Student) user;
        } else if(currentUser.getRole().equals("Editor")) {
            this.editor = (Editor) user;
        } else if(currentUser.getRole().equals("Admin")) {
            this.admin = (Admin) user;
        }
        if(search_results_list != null) {
            refreshQuestions();
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
        refreshQuestions();
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
    private void searchQuestions(ActionEvent event) {
        refreshQuestions();
    }

    @FXML
    private void toggleFilters(ActionEvent event) {
        boolean isVisible = search_filter_row.isVisible();
        search_filter_row.setVisible(!isVisible);
        search_filter_row.setManaged(!isVisible);
    }

    @FXML
    private void refreshQuestions() {
        search_results_list.getChildren().clear();
        ArrayList<Question> questions = getFilteredQuestions();
        if(questions.isEmpty()) {
            Label emptyLabel = new Label("No questions match this search.");
            emptyLabel.getStyleClass().add("search-empty-label");
            search_results_list.getChildren().add(emptyLabel);
            return;
        }
        for(Question question : questions) {
            search_results_list.getChildren().add(buildQuestionCard(question));
        }
    }

    @FXML
    private void startQuestion(ActionEvent event) {
        try {
            Question question = getQuestionFromEvent(event);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/question.fxml"));
            Parent root = loader.load();
            QuestionController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
            controller.setQuestion(question);
            app.selectQuestion(question);
            App.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    @FXML
    private void editQuestion(ActionEvent event) {
        try {
            Question question = getQuestionFromEvent(event);
            if (!canEditQuestion(question)) {
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/editQuestion.fxml"));
            Parent root = loader.load();
            EditQuestionController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
            controller.setQuestion(question);
            App.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    private ArrayList<Question> getFilteredQuestions() {
        QuestionType type = getSelectedType();
        Difficulty difficulty = getSelectedDifficulty();
        return app.searchQuestionsByTitle(textfield_search_bottom.getText(), type, difficulty);
    }

    private HBox buildQuestionCard(Question question) {
        HBox card = new HBox(16);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMinHeight(86);
        card.setPrefHeight(86);
        card.setMaxWidth(Double.MAX_VALUE);
        card.getStyleClass().add("search-card");

        StackPane avatar = new StackPane();
        avatar.setMinSize(36, 36);
        avatar.setPrefSize(36, 36);
        avatar.setMaxSize(36, 36);
        avatar.getStyleClass().add("search-card-icon-box");

        Label avatarLabel = new Label("Q");
        avatarLabel.getStyleClass().add("search-card-icon-label");
        avatar.getChildren().add(avatarLabel);

        VBox info = new VBox(6);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label title = new Label(getQuestionTitle(question));
        title.getStyleClass().add("search-card-title");

        Label description = new Label(buildDescription(question));
        description.setWrapText(true);
        description.getStyleClass().add("search-card-description");

        HBox tagRow = new HBox(8);
        Label tagLabel = new Label("Tags:");
        tagLabel.getStyleClass().add("search-card-tag-label");
        Label tagValue = new Label(getPrimaryTag(question));
        tagValue.getStyleClass().add("search-card-tag-pill");
        Label metaValue = new Label(getAuthorName(question) + " | " + getTypeText(question) + " | " + getDifficultyText(question));
        metaValue.getStyleClass().add("search-card-tag-label");
        tagRow.getChildren().addAll(tagLabel, tagValue, metaValue);

        info.getChildren().addAll(title, description, tagRow);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button startButton = new Button("Start");
        startButton.setUserData(question.getId().toString());
        startButton.setOnAction(event -> startQuestion(event));
        startButton.getStyleClass().add("search-card-button");
        startButton.setMinHeight(34);
        startButton.setMinWidth(98);

        card.getChildren().addAll(avatar, info, spacer, startButton);
        /*
        if (canEditQuestion(question)) {
            Button editButton = new Button("Edit");
            editButton.setUserData(question.getId().toString());
            editButton.setOnAction(event -> editQuestion(event));
            editButton.getStyleClass().add("search-card-button");
            card.getChildren().add(editButton);
        }*/
        return card;
    }

    private QuestionType getSelectedType() {
        String selectedType = type_filter_box.getValue();
        if(selectedType == null || selectedType.equals("ALL TYPES")) {
            return null;
        }
        return QuestionType.valueOf(selectedType);
    }

    private Difficulty getSelectedDifficulty() {
        String selectedDifficulty = difficulty_filter_box.getValue();
        if(selectedDifficulty == null || selectedDifficulty.equals("ALL DIFFICULTIES")) {
            return null;
        }
        return Difficulty.valueOf(selectedDifficulty);
    }

    private String getAuthorName(Question question) {
        if(question.getAuthor() == null || question.getAuthor().getUsername() == null || question.getAuthor().getUsername().isBlank()) {
            return "Unknown";
        }
        return question.getAuthor().getUsername();
    }

    private String getQuestionTitle(Question question) {
        if(question.getTitle() == null || question.getTitle().isBlank()) {
            return "Untitled Question";
        }
        return question.getTitle();
    }

    private String buildDescription(Question question) {
        String description = question.getDescription() == null ? "" : question.getDescription().trim();
        if(description.isBlank()) {
            return "No description provided.";
        }
        return description;
    }

    private String getPrimaryTag(Question question) {
        if(question.getTag() == null || question.getTag().isEmpty()) {
            return "NONE";
        }
        return question.getTag().get(0).toString();
    }

    private String getTypeText(Question question) {
        if(question.getType() == null) {
            return "NO TYPE";
        }
        return question.getType().toString();
    }

    private String getDifficultyText(Question question) {
        if(question.getDifficulty() == null) {
            return "NO DIFFICULTY";
        }
        return question.getDifficulty().toString();
    }

    private Question getQuestionFromEvent(ActionEvent event) {
        Button button = (Button) event.getSource();
        String id = (String) button.getUserData();
        return QuestionList.getInstance().getQuestion(UUID.fromString(id));
    }
    /*
    private boolean canEditQuestion(Question question) {
        if (question == null || currentUser == null || question.getAuthor() == null) {
            return false;
        }
        return currentUser.getID().equals(question.getAuthor().getID());
    }*/
}
