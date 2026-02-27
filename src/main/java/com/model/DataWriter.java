package com.model;


public class DataWriter {
    public static boolean saveUsers() {
        return true;
    }

    public static boolean saveQuestions() {
        return false;
    }
}

public static void main(String[] args){
    DataWriter.saveUsers();
}