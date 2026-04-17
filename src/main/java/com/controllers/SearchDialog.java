package com.controllers;

import com.model.Question;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javafx.scene.layout.VBox;

public class SearchDialog {
    private Question qSearch;
    Text qTitle;
    Text qDesc;
    Text qTags;
    Button qStartBtn;
    AnchorPane qAnchorPane;

    public SearchDialog(Question question) {
        super();
        this.setTitle("Question Details");
        this.qSearch = question;
        buildUI();
    }

    private void buildUI() {
        vbox = new VBox();
        qTitle = new Text(qSearch.getTitle());
        qDesc = new Text(qSearch.getDescription());
        qTags = new Text(String.join(", ", qSearch.get()));
    }
    
}
