package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "languages")
public class DTOlanguages {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String languageName;
    private String languageLevel;
    private String email;

    // Constructor
    public DTOlanguages(int id, String languageName, String languageLevel, String email) {
        this.id = id;
        this.languageName = languageName;
        this.languageLevel = languageLevel;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

