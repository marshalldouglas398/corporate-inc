package com.controllers;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.corporate.App;
import com.model.Admin;
import com.model.Course;
import com.model.Difficulty;
import com.model.Discipline;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.Question;
import com.model.QuestionList;
import com.model.QuestionTag;
import com.model.QuestionType;
import com.model.Section;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditQuestionController {
    @FXML
    private Button btn_home;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_settings;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_back;

    @FXML
    private Button addSectionButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField questionTitleField;

    @FXML
    private ComboBox<String> questionDifficultyField;

    @FXML
    private ComboBox<String> questionTypeField;

    @FXML
    private TextField questionTagsField;

    @FXML
    private ComboBox<String> questionDisciplinesField;

    @FXML
    private ComboBox<String> questionCoursesField;

    @FXML
    private TextArea questionDescriptionArea;

    @FXML
    private CheckBox interviewModeCheckBox;

    @FXML
    private TextField interviewTimeField;

    @FXML
    private TextField questionHintOneField;

    @FXML
    private TextField questionHintTwoField;

    @FXML
    private TextField questionHintThreeField;

    @FXML
    private TextField sectionOneTitleField;

    @FXML
    private TextArea sectionOneDescriptionArea;

    @FXML
    private TextField sectionOneFileField;

    @FXML
    private TextArea sectionOneCodeArea;

    @FXML
    private Label sectionStatusLabel;

    private User currentUser;
    private Admin admin;
    private Editor editor;
    private InterviewApplication app = new InterviewApplication();
    private Question currentQuestion;
    private int currentSectionIndex = 0;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    public void setQuestion(Question question) {
        this.currentQuestion = question;
        this.currentSectionIndex = 0;
        reloadCurrentQuestion();
    }

    @FXML
    private void initialize() {
        questionTypeField.getItems().setAll(enumNames(QuestionType.values()));
        questionDifficultyField.getItems().setAll(enumNames(Difficulty.values()));
        questionDisciplinesField.getItems().setAll(enumNames(Discipline.values()));
        questionCoursesField.getItems().setAll(enumNames(Course.values()));
        loadFirstQuestion();
    }

    public void setUser(User user) {
        this.currentUser = user;
        this.admin = null;
        this.editor = null;
        if (user instanceof Admin) {
            this.admin = (Admin) user;
        } else if (user instanceof Editor) {
            this.editor = (Editor) user;
        }
        applyEditingState();
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        goBackToRoleDashboard();
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        goBackToRoleDashboard();
    }

    @FXML
    private void cancelEdit(ActionEvent event) throws IOException {
        reloadCurrentQuestion();
    }

    @FXML
    private void saveChanges(ActionEvent event) throws IOException {
        if (currentQuestion == null) {
            showInfo("No question available to save.");
            return;
        }
        if (!canCurrentUserEdit()) {
            showInfo("Only the author of this question can edit it.");
            return;
        }

        String title = safeText(questionTitleField);
        String description = safeTextArea(questionDescriptionArea);
        if (title.isEmpty() || description.isEmpty()) {
            showInfo("Title and description are required.");
            return;
        }

        currentQuestion.setTitle(title);
        currentQuestion.setDescription(description);
        currentQuestion.setType(parseEnum(QuestionType.class, questionTypeField.getValue()));
        currentQuestion.setDifficulty(parseEnum(Difficulty.class, questionDifficultyField.getValue()));

        ArrayList<Discipline> disciplines = new ArrayList<>();
        Discipline selectedDiscipline = parseEnum(Discipline.class, questionDisciplinesField.getValue());
        if (selectedDiscipline != null) {
            disciplines.add(selectedDiscipline);
        }
        currentQuestion.setDiscipline(disciplines);

        ArrayList<Course> courses = new ArrayList<>();
        Course selectedCourse = parseEnum(Course.class, questionCoursesField.getValue());
        if (selectedCourse != null) {
            courses.add(selectedCourse);
        }
        currentQuestion.setCourse(courses);

        currentQuestion.setTag(parseTags(questionTagsField.getText()));

        ArrayList<String> hints = new ArrayList<>();
        addIfNotBlank(hints, questionHintOneField.getText());
        addIfNotBlank(hints, questionHintTwoField.getText());
        addIfNotBlank(hints, questionHintThreeField.getText());
        currentQuestion.setHints(hints);

        int interviewTime = -1;
        if (interviewModeCheckBox.isSelected()) {
            String timeText = safeText(interviewTimeField);
            if (!timeText.isEmpty()) {
                try {
                    interviewTime = Integer.parseInt(timeText);
                } catch (NumberFormatException e) {
                    showInfo("Interview time must be a whole number.");
                    return;
                }
            }
        }
        currentQuestion.setInterviewMode(interviewModeCheckBox.isSelected(), interviewTime);

        upsertVisibleSection();

        QuestionList.getInstance().save();
        showInfo("Question changes saved.");
        reloadCurrentQuestion();
    }

    @FXML
    private void addSection(ActionEvent event) {
        if (currentQuestion == null) {
            return;
        }
        if (!canCurrentUserEdit()) {
            showInfo("Only the author of this question can edit it.");
            return;
        }
        upsertVisibleSection();
        currentQuestion.getSections().add(new Section());
        currentSectionIndex = currentQuestion.getSections().size() - 1;
        loadSectionIntoForm();
    }

    @FXML
    private void logout() throws IOException {
        if (currentUser != null) {
            app.logout(currentUser);
        }
        App.setRoot("login");
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
    private void goToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    private void goBackToRoleDashboard() throws IOException {
        FXMLLoader loader;
        if (admin != null) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
            Parent root = loader.load();
            DashAController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
            App.setRoot(root);
        } else if (editor != null) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
            Parent root = loader.load();
            DashEController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
            App.setRoot(root);
        } else {
            App.setRoot("login");
        }
    }

    private void loadFirstQuestion() {
        if (currentQuestion != null) {
            reloadCurrentQuestion();
            return;
        }
        List<Question> questions = QuestionList.getInstance().getQuestions();
        if (questions == null || questions.isEmpty()) {
            currentQuestion = null;
            clearForm();
            sectionStatusLabel.setText("No questions available.");
            applyEditingState();
            return;
        }
        currentQuestion = questions.get(0);
        currentSectionIndex = 0;
        reloadCurrentQuestion();
    }

    private void reloadCurrentQuestion() {
        if (currentQuestion == null) {
            clearForm();
            sectionStatusLabel.setText("No question loaded.");
            applyEditingState();
            return;
        }

        questionTitleField.setText(nullSafe(currentQuestion.getTitle()));
        questionDescriptionArea.setText(nullSafe(currentQuestion.getDescription()));
        questionTypeField.setValue(currentQuestion.getType() == null ? null : currentQuestion.getType().name());
        questionDifficultyField.setValue(currentQuestion.getDifficulty() == null ? null : currentQuestion.getDifficulty().name());

        questionDisciplinesField.setValue(
            currentQuestion.getDiscipline().isEmpty() ? null : currentQuestion.getDiscipline().get(0).name()
        );
        questionCoursesField.setValue(
            currentQuestion.getCourse().isEmpty() ? null : currentQuestion.getCourse().get(0).name()
        );
        questionTagsField.setText(
            currentQuestion.getTag().stream().map(Enum::name).collect(Collectors.joining(", "))
        );

        questionHintOneField.setText(currentQuestion.getHints().size() > 0 ? nullSafe(currentQuestion.getHints().get(0)) : "");
        questionHintTwoField.setText(currentQuestion.getHints().size() > 1 ? nullSafe(currentQuestion.getHints().get(1)) : "");
        questionHintThreeField.setText(currentQuestion.getHints().size() > 2 ? nullSafe(currentQuestion.getHints().get(2)) : "");

        interviewModeCheckBox.setSelected(currentQuestion.isInterviewMode());
        interviewTimeField.setText(currentQuestion.getTime() < 0 ? "" : Integer.toString(currentQuestion.getTime()));

        if (currentQuestion.getSections().isEmpty()) {
            currentQuestion.getSections().add(new Section());
            currentSectionIndex = 0;
        } else if (currentSectionIndex >= currentQuestion.getSections().size()) {
            currentSectionIndex = currentQuestion.getSections().size() - 1;
        }
        loadSectionIntoForm();
        applyEditingState();
    }

    private void upsertVisibleSection() {
        if (currentQuestion == null) {
            return;
        }
        ArrayList<Section> sections = currentQuestion.getSections();
        if (sections.isEmpty()) {
            sections.add(new Section());
            currentSectionIndex = 0;
        } else if (currentSectionIndex < 0 || currentSectionIndex >= sections.size()) {
            currentSectionIndex = 0;
        }

        Section section = sections.get(currentSectionIndex);
        section.setTitle(safeText(sectionOneTitleField));
        section.setDescription(safeTextArea(sectionOneDescriptionArea));
        String fileText = safeText(sectionOneFileField);
        section.setFile(fileText.isEmpty() ? null : new File(fileText));
        section.setCode(safeTextArea(sectionOneCodeArea));
    }

    private void loadSectionIntoForm() {
        if (currentQuestion == null || currentQuestion.getSections().isEmpty()) {
            sectionOneTitleField.setText("");
            sectionOneDescriptionArea.setText("");
            sectionOneFileField.setText("");
            sectionOneCodeArea.setText("");
            sectionStatusLabel.setText("No section loaded.");
            return;
        }

        Section section = currentQuestion.getSections().get(currentSectionIndex);
        sectionOneTitleField.setText(nullSafe(section.getTitle()));
        sectionOneDescriptionArea.setText(nullSafe(section.getDescription()));
        sectionOneFileField.setText(section.getFile() == null ? "" : section.getFile().toString());
        sectionOneCodeArea.setText(nullSafe(section.getCode()));
        sectionStatusLabel.setText("Editing section " + (currentSectionIndex + 1) + " of " + currentQuestion.getSections().size());
    }

    private ArrayList<QuestionTag> parseTags(String rawTags) {
        ArrayList<QuestionTag> tags = new ArrayList<>();
        if (rawTags == null || rawTags.trim().isEmpty()) {
            return tags;
        }
        String[] parts = rawTags.split(",");
        for (String part : parts) {
            QuestionTag parsed = parseEnum(QuestionTag.class, part);
            if (parsed != null && !tags.contains(parsed)) {
                tags.add(parsed);
            }
        }
        return tags;
    }

    private <E extends Enum<E>> E parseEnum(Class<E> enumClass, String raw) {
        if (raw == null) {
            return null;
        }
        String normalized = raw.trim().toUpperCase();
        if (normalized.isEmpty()) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, normalized);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private List<String> enumNames(Enum<?>[] values) {
        return Arrays.stream(values).map(Enum::name).collect(Collectors.toList());
    }

    private void addIfNotBlank(ArrayList<String> list, String value) {
        String cleaned = value == null ? "" : value.trim();
        if (!cleaned.isEmpty()) {
            list.add(cleaned);
        }
    }

    private String safeText(TextField field) {
        return field.getText() == null ? "" : field.getText().trim();
    }

    private String safeTextArea(TextArea field) {
        return field.getText() == null ? "" : field.getText().trim();
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }

    private void clearForm() {
        questionTitleField.setText("");
        questionDifficultyField.setValue(null);
        questionTypeField.setValue(null);
        questionTagsField.setText("");
        questionDisciplinesField.setValue(null);
        questionCoursesField.setValue(null);
        questionDescriptionArea.setText("");
        interviewModeCheckBox.setSelected(false);
        interviewTimeField.setText("");
        questionHintOneField.setText("");
        questionHintTwoField.setText("");
        questionHintThreeField.setText("");
        sectionOneTitleField.setText("");
        sectionOneDescriptionArea.setText("");
        sectionOneFileField.setText("");
        sectionOneCodeArea.setText("");
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Question");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void applyEditingState() {
        boolean canEdit = canCurrentUserEdit();
        questionTitleField.setDisable(!canEdit);
        questionDescriptionArea.setDisable(!canEdit);
        questionTypeField.setDisable(!canEdit);
        questionDifficultyField.setDisable(!canEdit);
        questionDisciplinesField.setDisable(!canEdit);
        questionCoursesField.setDisable(!canEdit);
        questionTagsField.setDisable(!canEdit);
        interviewModeCheckBox.setDisable(!canEdit);
        interviewTimeField.setDisable(!canEdit);
        questionHintOneField.setDisable(!canEdit);
        questionHintTwoField.setDisable(!canEdit);
        questionHintThreeField.setDisable(!canEdit);
        sectionOneTitleField.setDisable(!canEdit);
        sectionOneDescriptionArea.setDisable(!canEdit);
        sectionOneFileField.setDisable(!canEdit);
        sectionOneCodeArea.setDisable(!canEdit);
        cancelButton.setDisable(!canEdit);
        if (saveButton != null) {
            saveButton.setDisable(!canEdit);
        }
        if (addSectionButton != null) {
            addSectionButton.setDisable(!canEdit);
        }
        if (sectionStatusLabel != null && currentQuestion != null && !canEdit) {
            sectionStatusLabel.setText("Read-only: only the question author can edit this question.");
        }
    }

    private boolean canCurrentUserEdit() {
        if (currentUser == null || currentQuestion == null || currentQuestion.getAuthor() == null) {
            return false;
        }
        UUID currentUserId = currentUser.getID();
        UUID authorId = currentQuestion.getAuthor().getID();
        return currentUserId != null && currentUserId.equals(authorId);
    }
}
