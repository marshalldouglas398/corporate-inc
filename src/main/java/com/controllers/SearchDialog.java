package com.controllers;

import com.model.Question;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class SearchDialog {
    private Question qSearch;
    private Text qTitle;
    private Text qDesc;
    private Text qTags;
    private Button qStartBtn;
    private AnchorPane qAnchorPane;
    private VBox vbox;

    public SearchDialog(Question question) {
        this.qSearch = question;
        buildUI();
    }

    private void buildUI() {
        vbox = new VBox();
        qAnchorPane = new AnchorPane();
        qTitle = new Text(qSearch == null ? "" : qSearch.getTitle());
        qDesc = new Text(qSearch == null ? "" : qSearch.getDescription());
        qTags = new Text(qSearch == null ? "" : qSearch.getTag().toString());
        qStartBtn = new Button("Start");
        vbox.getChildren().addAll(qTitle, qDesc, qTags, qStartBtn);
        qAnchorPane.getChildren().add(vbox);
    }
    
}
