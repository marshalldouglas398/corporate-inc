package com.model;

import java.io.File;
/**
 * This class represents a section of a question or comment
 * @author Eden Moore(idk if we need this edit later if needed) + Marshall Pigford
 */
public class Section {
    private String title;
    private String description;
    private File file;
    private String code;

    /**
     * Parameterized constructor for the Section class
     * @param title The title of the section
     * @param description The description of the section
     * @param file The file associated with the section (if any)
     * @param code The code associated with the section (if any)
     */
    public Section(String title, String description, File file, String code) {
        this.title = title;
        this.description = description;
        this.file = file;
        this.code = code;
    }
}
