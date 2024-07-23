package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "template")
public class DTOTemplate {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int templetename;
    private String email;

    // Constructor
    public DTOTemplate(int id, int templetename, String email) {
        this.id = id;
        this.templetename = templetename;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTempletename() {
        return templetename;
    }

    public void setTempletename(int templetename) {
        this.templetename = templetename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
