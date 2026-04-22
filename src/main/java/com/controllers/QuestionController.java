package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.corporate.App;
import com.model.Admin;
import com.model.Comment;
import com.model.CommentTag;
import com.model.Difficulty;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.Question;
import com.model.QuestionList;
import com.model.QuestionTag;
import com.model.QuestionType;
import com.model.Section;
import com.model.Student;
import com.model.User;
import com.model.UserList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuestionController {
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
    private Button edit_question_btn;

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

    @FXML
    private Label questionRatingLabel;

    @FXML
    private VBox examplesContainer;

    @FXML
    private VBox hintsCard;

    @FXML
    private VBox hintsContainer;

    @FXML
    private TextArea responseArea;

    @FXML
    private VBox draftSectionsContainer;

    @FXML
    private VBox draftTagsContainer;

    @FXML
    private VBox commentsContainer;

    @FXML
    private ComboBox<String> commentFilterBox;

    @FXML
    private Label replyingToLabel;

    private Question currentQuestion;
    private Student student;
    private Editor editor;
    private Admin admin;
    private User currentUser;
    private InterviewApplication app;
    private final ArrayList<Section> draftSections = new ArrayList<>();
    private final ArrayList<CommentTag> draftTags = new ArrayList<>();
    private Comment replyingTo;



    @FXML
    public void initialize() {
        commentFilterBox.getItems().add("ALL");
        for (CommentTag tag : CommentTag.values()) {
            commentFilterBox.getItems().add(tag.name());
        }
        commentFilterBox.setValue("ALL");

        if (currentQuestion == null) {
            ArrayList<Question> questions = QuestionList.getInstance().getQuestions();
            if (questions != null) {
                for (Question question : questions) {
                    if (question != null) {
                        currentQuestion = question;
                        break;
                    }
                }
            }
        }
        refreshView();
    }


    @FXML
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = null;
        if (student != null) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dash.fxml"));
        } else if (editor != null) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
        } else if (admin != null) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        }

        if (loader == null) {
            return;
        }

        Parent root = loader.load();
        if (student != null) {
            DashController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
        } else if (editor != null) {
            DashEController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
        } else if (admin != null) {
            DashAController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
        }
        App.setRoot(root);
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/settings.fxml"));
        Parent root = loader.load();
        SettingsController controller = loader.getController();
        if (currentUser != null) {
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
        }
        App.setRoot(root);
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        if (currentUser != null) {
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
        }
        App.setRoot(root);
    }

    @FXML
    private void editCurrentQuestion(ActionEvent event) throws IOException {
        if (!canEditCurrentQuestion()) {
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/editQuestion.fxml"));
        Parent root = loader.load();
        EditQuestionController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        controller.setQuestion(currentQuestion);
        App.setRoot(root);
    }

    public void setQuestion(Question question) {
        this.currentQuestion = question;
        refreshView();
    }

    public void setUser(User user) {
        this.currentUser = user;
        this.student = null;
        this.editor = null;
        this.admin = null;
        if (user instanceof Student) {
            this.student = (Student) user;
        } else if (user instanceof Editor) {
            this.editor = (Editor) user;
        } else if (user instanceof Admin) {
            this.admin = (Admin) user;
        }
        refreshView();
    }

    public void setInterviewApplication(InterviewApplication app) {
        if (app != null) {
            this.app = app;
        }
    }

    @FXML
    private void addSectionDraft(ActionEvent event) {
        if (!isEditable()) {
            return;
        }
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Add Section");
        titleDialog.setHeaderText("Section title");
        Optional<String> titleResult = titleDialog.showAndWait();
        if (titleResult.isEmpty()) {
            return;
        }

        TextInputDialog descriptionDialog = new TextInputDialog();
        descriptionDialog.setTitle("Add Section");
        descriptionDialog.setHeaderText("Section description");
        Optional<String> descriptionResult = descriptionDialog.showAndWait();
        if (descriptionResult.isEmpty()) {
            return;
        }

        String title = titleResult.get().trim();
        String description = descriptionResult.get().trim();
        if (title.isEmpty() && description.isEmpty()) {
            return;
        }
        draftSections.add(new Section(title, description, null, null));
        rebuildDraftViews();
    }

    @FXML
    private void addTagDraft(ActionEvent event) {
        if (!isEditable()) {
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Tag");
        dialog.setHeaderText("Enter a tag name from: " + joinCommentTags());
        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return;
        }
        String raw = result.get().trim().toUpperCase();
        if (raw.isEmpty()) {
            return;
        }
        try {
            CommentTag tag = CommentTag.valueOf(raw);
            if (!draftTags.contains(tag)) {
                draftTags.add(tag);
            }
            rebuildDraftViews();
        } catch (IllegalArgumentException ignored) {
            // Invalid tag; no-op to keep existing UX simple.
        }
    }

    @FXML
    private void submitComment(ActionEvent event) {
        if (!isEditable() || currentQuestion == null) {
            return;
        }
        String body = responseArea.getText() == null ? "" : responseArea.getText().trim();
        if (body.isEmpty() && draftSections.isEmpty()) {
            return;
        }

        ArrayList<CommentTag> tags = new ArrayList<>(draftTags);
        if (tags.isEmpty()) {
            tags.add(CommentTag.DISCUSSION);
        }
        String title = makeCommentTitle(body);
        boolean isQuestionAuthor = currentQuestion.getAuthor() != null && currentQuestion.getAuthor().equals(currentUser);
        Comment created = new Comment(title, body, currentUser, tags, new ArrayList<>(draftSections), isQuestionAuthor);
        if (replyingTo != null) {
            replyingTo.addReply(created);
            replyingTo = null;
        } else {
            currentQuestion.addComment(created);
        }

        if (student != null && tags.contains(CommentTag.SOLUTION)) {
            if (!student.getQuestionsAnswered().contains(currentQuestion.getId())) {
                currentQuestion.completeQuestion(student);
            }
            UserList.getInstance().save();
        }

        QuestionList.getInstance().save();
        responseArea.clear();
        draftSections.clear();
        draftTags.clear();
        rebuildDraftViews();
        refreshView();
    }

    @FXML
    private void onCommentFilterChanged(ActionEvent event) {
        refreshComments();
    }

    @FXML
    private void rateQuestion(ActionEvent event) {
        if (!isEditable() || currentQuestion == null) {
            return;
        }
        Optional<Double> rating = promptRating("Rate Question");
        if (rating.isEmpty()) {
            return;
        }
        app.rateQuestion(currentQuestion, rating.get());
        QuestionList.getInstance().save();
        refreshView();
    }

    private void rateComment(Comment comment) {
        if (!isEditable() || comment == null) {
            return;
        }
        Optional<Double> rating = promptRating("Rate Comment");
        if (rating.isEmpty()) {
            return;
        }
        app.rateComment(comment, rating.get());
        QuestionList.getInstance().save();
        refreshComments();
    }

    /**
     * Rating dialog: text field only accepts a single digit 1–5 (no other characters can be entered).
     * OK with an empty field shows a warning.
     */
    private Optional<Double> promptRating(String title) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("Enter a single digit from 1 to 5");

        TextField field = new TextField();
        field.setMaxWidth(72);
        field.setPromptText("1–5");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change;
            }
            if (newText.length() > 1) {
                return null;
            }
            char c = newText.charAt(0);
            if (c >= '1' && c <= '5') {
                return change;
            }
            return null;
        };
        field.setTextFormatter(new TextFormatter<>(filter));

        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Rating:"), field);
        dialog.getDialogPane().setContent(content);
        ButtonType okType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okType, ButtonType.CANCEL);

        Optional<ButtonType> choice = dialog.showAndWait();
        if (choice.isEmpty() || choice.get() != okType) {
            return Optional.empty();
        }

        String s = field.getText() == null ? "" : field.getText().trim();
        if (s.isEmpty()) {
            showRatingWarning("Please enter a rating from 1 to 5.");
            return Optional.empty();
        }
        try {
            int value = Integer.parseInt(s);
            if (value < 1 || value > 5) {
                showRatingWarning("Rating must be a whole number from 1 to 5.");
                return Optional.empty();
            }
            return Optional.of((double) value);
        } catch (NumberFormatException ex) {
            showRatingWarning("Rating must be a whole number from 1 to 5.");
            return Optional.empty();
        }
    }

    private void showRatingWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid rating");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshView() {
        if (questionTitleLabel == null) {
            return;
        }

        if (currentQuestion == null) {
            questionTitleLabel.setText("No question available");
            q_desc_label.setText("");
            qTypeLabel.setText("");
            qTagLabel.setText("");
            difficultyLabel.setText("");
            questionRatingLabel.setText("-");
            examplesContainer.getChildren().setAll(new Label("No examples available."));
            hintsContainer.getChildren().clear();
            hintsCard.setVisible(false);
            hintsCard.setManaged(false);
            commentsContainer.getChildren().setAll(new Label("No comments yet."));
            setEditableState(false);
            setQuestionEditState(false);
            return;
        }

        questionTitleLabel.setText(safe(currentQuestion.getTitle()));
        q_desc_label.setText(safe(currentQuestion.getDescription()));
        setType(currentQuestion.getType());
        setDiff(currentQuestion.getDifficulty());
        qTagLabel.setText(formatQuestionTags(currentQuestion.getTag()));
        questionRatingLabel.setText(formatRating(currentQuestion.getRating(), currentQuestion.getNumRatings()));
        refreshExamples();
        refreshHints();
        refreshComments();
        rebuildDraftViews();
        setEditableState(isEditable());
        setQuestionEditState(canEditCurrentQuestion());
    }

    private void refreshExamples() {
        examplesContainer.getChildren().clear();
        ArrayList<Section> sections = currentQuestion.getSections();
        if (sections == null || sections.isEmpty()) {
            examplesContainer.getChildren().add(new Label("No examples available."));
            return;
        }
        for (Section section : sections) {
            VBox card = new VBox(4);
            Label title = new Label(safe(section.getTitle()));
            Label description = new Label(safe(section.getDescription()));
            description.setWrapText(true);
            card.getChildren().add(title);
            if (!description.getText().isBlank()) {
                card.getChildren().add(description);
            }
            if (section.getCode() != null && !section.getCode().isBlank()) {
                Label code = new Label(section.getCode());
                code.setWrapText(true);
                card.getChildren().add(code);
            }
            if (section.getFile() != null) {
                Label file = new Label(section.getFile().getPath());
                file.setWrapText(true);
                card.getChildren().add(file);
            }
            examplesContainer.getChildren().add(card);
        }
    }

    private void refreshHints() {
        hintsContainer.getChildren().clear();
        ArrayList<String> hints = currentQuestion.getHints();
        if (hints == null) {
            hintsCard.setVisible(false);
            hintsCard.setManaged(false);
            return;
        }

        int hintCount = 0;
        for (String hint : hints) {
            if (hint == null || hint.isBlank()) {
                continue;
            }
            hintCount++;
            Label title = new Label("Hint " + hintCount);
            Label body = new Label(hint.trim());
            body.setWrapText(true);
            hintsContainer.getChildren().addAll(title, body);
        }
        boolean hasHints = hintCount > 0;
        hintsCard.setVisible(hasHints);
        hintsCard.setManaged(hasHints);
    }

    private void refreshComments() {
        commentsContainer.getChildren().clear();
        if (currentQuestion == null) {
            commentsContainer.getChildren().add(new Label("No comments yet."));
            return;
        }
        List<Comment> comments = filterComments(currentQuestion.getComments());
        if (comments.isEmpty()) {
            commentsContainer.getChildren().add(new Label("No comments yet."));
            return;
        }
        for (Comment comment : comments) {
            commentsContainer.getChildren().add(buildCommentNode(comment, 0));
        }
    }

    private List<Comment> filterComments(List<Comment> allComments) {
        if (allComments == null) {
            return List.of();
        }
        String selected = commentFilterBox.getValue();
        if (selected == null || "ALL".equals(selected)) {
            return allComments;
        }
        return allComments.stream()
                .filter(comment -> hasTag(comment, selected))
                .collect(Collectors.toList());
    }

    private boolean hasTag(Comment comment, String selectedTag) {
        if (comment.getTags() == null) {
            return false;
        }
        for (CommentTag tag : comment.getTags()) {
            if (tag.name().equals(selectedTag)) {
                return true;
            }
        }
        return false;
    }

    private VBox buildCommentNode(Comment comment, int depth) {
        VBox box = new VBox(6);
        box.setPadding(new Insets(0, 0, 0, depth * 16.0));

        String authorName = comment.getAuthor() == null ? "unknown" : comment.getAuthor().getUsername();
        Label header = new Label(authorName + "  " + formatCommentTags(comment.getTags()) + "  Rating " + formatSimpleRating(comment.getRating()));
        Label body = new Label(safe(comment.getComment()));
        body.setWrapText(true);
        body.getStyleClass().add("question-content-text");
        box.getChildren().addAll(header, body);

        if (comment.getSections() != null) {
            for (Section section : comment.getSections()) {
                String text = safe(section.getTitle());
                if (!safe(section.getDescription()).isBlank()) {
                    text = text + ": " + section.getDescription();
                }
                Label sectionLabel = new Label(text);
                sectionLabel.setWrapText(true);
                sectionLabel.getStyleClass().add("question-content-text");
                box.getChildren().add(sectionLabel);
            }
        }

        HBox actions = new HBox(8);
        Button replyButton = new Button("Reply");
        replyButton.getStyleClass().add("action-button");
        replyButton.setDisable(!isEditable());
        replyButton.setOnAction(e -> {
            replyingTo = comment;
            replyingToLabel.setText("Replying to " + authorName);
            replyingToLabel.setVisible(true);
        });
        Button rateButton = new Button("Rate");
        rateButton.getStyleClass().add("action-button");
        rateButton.setDisable(!isEditable());
        rateButton.setOnAction(e -> rateComment(comment));
        actions.getChildren().addAll(replyButton, rateButton);
        box.getChildren().add(actions);

        ArrayList<Comment> replies = comment.getReplies();
        if (replies != null && !replies.isEmpty()) {
            VBox repliesBox = new VBox(8);
            for (Comment reply : replies) {
                repliesBox.getChildren().add(buildCommentNode(reply, depth + 1));
            }
            box.getChildren().add(repliesBox);
        }

        return box;
    }

    private void rebuildDraftViews() {
        draftSectionsContainer.getChildren().clear();
        if (draftSections.isEmpty()) {
            draftSectionsContainer.getChildren().add(new Label("No sections added."));
        } else {
            for (int i = 0; i < draftSections.size(); i++) {
                Section section = draftSections.get(i);
                String sectionText = safe(section.getTitle());
                if (!safe(section.getDescription()).isBlank()) {
                    sectionText += ": " + section.getDescription();
                }
                if (sectionText.isBlank()) {
                    sectionText = "Section " + (i + 1);
                }
                draftSectionsContainer.getChildren().add(new Label(sectionText));
            }
        }

        draftTagsContainer.getChildren().clear();
        if (draftTags.isEmpty()) {
            draftTagsContainer.getChildren().add(new Label("No tags selected."));
        } else {
            for (CommentTag tag : draftTags) {
                draftTagsContainer.getChildren().add(new Label(tag.name()));
            }
        }

        if (replyingTo == null) {
            replyingToLabel.setVisible(false);
            replyingToLabel.setText("");
        }
    }

    private void setEditableState(boolean editable) {
        responseArea.setDisable(!editable);
        add_sec_btn.setDisable(!editable);
        add_t_btn.setDisable(!editable);
        sub_com_btn.setDisable(!editable);
    }

    private boolean isEditable() {
        return currentUser != null;
    }

    private boolean canEditCurrentQuestion() {
        if (currentUser == null || currentQuestion == null || currentQuestion.getAuthor() == null) {
            return false;
        }
        return currentUser.getID().equals(currentQuestion.getAuthor().getID());
    }

    private void setQuestionEditState(boolean canEditQuestion) {
        if (edit_question_btn == null) {
            return;
        }
        edit_question_btn.setVisible(canEditQuestion);
        edit_question_btn.setManaged(canEditQuestion);
    }

    private String formatQuestionTags(List<QuestionTag> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        return tags.stream().map(Enum::name).collect(Collectors.joining(", "));
    }

    private String formatCommentTags(List<CommentTag> tags) {
        if (tags == null || tags.isEmpty()) {
            return "DISCUSSION";
        }
        return tags.stream().map(Enum::name).collect(Collectors.joining(", "));
    }

    private String formatRating(Double rating, Double count) {
        if (rating == null || count == null || count <= 0) {
            return "-";
        }
        return String.format("%.2f/5 (%d)", rating, count.intValue());
    }

    private String formatSimpleRating(Double rating) {
        if (rating == null) {
            return "-";
        }
        return String.format("%.2f/5", rating);
    }

    private String makeCommentTitle(String body) {
        if (body == null || body.isBlank()) {
            return "Comment";
        }
        String oneLine = body.lines().findFirst().orElse("Comment").trim();
        return oneLine.length() > 40 ? oneLine.substring(0, 40) : oneLine;
    }

    private String joinCommentTags() {
        return Arrays.stream(CommentTag.values()).map(Enum::name).collect(Collectors.joining(", "));
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    @FXML
    public void setType(QuestionType t) {
        if (t == null) {
            qTypeLabel.setText("");
            return;
        }
        qTypeLabel.setText(t.name());
    }
    @FXML
    public void setDiff(Difficulty d) {
        if (d == null) {
            difficultyLabel.setText("");
            return;
        }
        difficultyLabel.setText(d.name());
    }
}
