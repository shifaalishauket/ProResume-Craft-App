package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Hobbies")
public class DTOHobbies {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String hobbyName;
    private String email;

    // Constructor
    public DTOHobbies(int id, String hobbyName, String email) {
        this.id = id;
        this.hobbyName = hobbyName;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

