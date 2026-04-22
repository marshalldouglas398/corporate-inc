package com.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import com.corporate.App;
import com.model.Admin;
import com.model.InterviewApplication;
import com.model.Question;
import com.model.QuestionList;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class AdminQController {

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_settings;

    @FXML
    private Label welcomeMessage;

    @FXML
    private Label text_dash_date;

    @FXML
    private TilePane questionsGrid;

    @FXML
    private Label emptyStateLabel;

    private Admin currentUser;
    private InterviewApplication app;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    public void setUser(User user) {
        this.currentUser = (Admin) user;
        if (app == null) {
            app = new InterviewApplication();
        }
        displayWelcome(user.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH);
        if (text_dash_date != null) {
            text_dash_date.setText(LocalDate.now().format(formatter));
        }
        loadQuestionCards();
    }

    @FXML
    public void displayWelcome(String username) {
        if (welcomeMessage != null) welcomeMessage.setText("Welcome, " + username + "!");
    }

    @FXML
    private void logout() throws IOException {
        if (app != null) app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    private void goToDash(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        Parent root = loader.load();
        DashAController dashA = loader.getController();
        dashA.setUser(currentUser);
        dashA.setInterviewApplication(app);
        App.setRoot(root);
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
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
    private void addQuestion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/createQuestion.fxml"));
        Parent root = loader.load();
        CreateQuestionController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    private void loadQuestionCards() {
        if (questionsGrid == null) return;
        questionsGrid.getChildren().clear();

        ArrayList<UUID> ids = currentUser.getQuestionsMade();
        QuestionList ql = QuestionList.getInstance();
        ArrayList<Question> questions = new ArrayList<>();
        for (Object raw : ids) {
            UUID id = toUUID(raw);
            if (id == null) continue;
            Question q = ql.getQuestion(id);
            if (q != null) questions.add(q);
        }

        if (questions.isEmpty()) {
            if (emptyStateLabel != null) {
                emptyStateLabel.setVisible(true);
                emptyStateLabel.setManaged(true);
            }
            return;
        }
        if (emptyStateLabel != null) {
            emptyStateLabel.setVisible(false);
            emptyStateLabel.setManaged(false);
        }
        for (Question q : questions) {
            questionsGrid.getChildren().add(buildCard(q));
        }
    }

    private VBox buildCard(Question question) {
        VBox card = new VBox(10);
        card.setMaxWidth(360);
        card.setMinWidth(360);
        card.setPrefWidth(360);
        card.getStyleClass().add("editor-q-card");
        card.setPadding(new Insets(12));

        Label title = new Label(safe(question.getTitle()));
        title.getStyleClass().add("editor-q-card-title");
        title.setWrapText(true);

        HBox metaRow = new HBox(8);
        metaRow.setAlignment(Pos.CENTER_LEFT);
        String tagText = question.getTag() != null && !question.getTag().isEmpty()
                ? question.getTag().get(0).name() : "-";
        Label tagLabel = new Label(tagText);
        tagLabel.getStyleClass().add("editor-q-card-tag");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String ratingText = question.getRating() != null
                ? String.format("%.1f / 5", question.getRating()) : "No rating";
        Label ratingLabel = new Label(ratingText);
        ratingLabel.getStyleClass().add("editor-q-card-rating");
        metaRow.getChildren().addAll(tagLabel, spacer, ratingLabel);

        String diffText = question.getDifficulty() != null ? question.getDifficulty().name() : "-";
        String typeText = question.getType() != null ? question.getType().name() : "-";
        Label metaDetail = new Label(typeText + "  |  " + diffText);
        metaDetail.getStyleClass().add("editor-q-card-body");

        String desc = question.getDescription() != null ? question.getDescription().trim() : "";
        if (desc.length() > 120) desc = desc.substring(0, 120) + "...";
        Label body = new Label(desc.isEmpty() ? "No description." : desc);
        body.getStyleClass().add("editor-q-card-body");
        body.setWrapText(true);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        Button editBtn = new Button("Edit");
        editBtn.getStyleClass().add("dashboard-start-button");
        editBtn.setOnAction(e -> openEditQuestion(question));
        buttonRow.getChildren().add(editBtn);

        card.getChildren().addAll(title, metaRow, metaDetail, body, buttonRow);
        return card;
    }

    private void openEditQuestion(Question question) {
        try {
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
    }

    private UUID toUUID(Object raw) {
        if (raw instanceof UUID) return (UUID) raw;
        if (raw instanceof String) {
            try { return UUID.fromString((String) raw); } catch (Exception ignored) {}
        }
        return null;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
