package com.model;

import java.io.File;
/**
 * This class represents a section of a question or comment
 * @author Eden Moore(idk if we need this or not edit later if needed)
 */
public class section {
    private String title;
    private String description;
    private File file;
    private String code;

    public section(String title, String description, File file, String code) {
        this.title = title;
        this.description = description;
        this.file = file;
        this.code = code;
    }
}
