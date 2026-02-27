package com.model;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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