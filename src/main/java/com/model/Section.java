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

    /**
     * Gets the title of the section
     * @return The title of the section
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the description of the section
     * @return The description of the section
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the file associated with the section
     * @return The file associated with the section
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Gets the code associated with the section
     * @return The code associated with the section
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Sets the title of the section
     * @param title The new title to set for the section
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the section
     * @param description The new description to set for the section
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the file associated with the section
     * @param file The new file to set for the section
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Sets the code associated with the section
     * @param code The new code to set for the section
     */
    public void setCode(String code) {
        this.code = code;
    }
}
