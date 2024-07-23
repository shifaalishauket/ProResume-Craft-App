package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "savedresume")
public class DTOSavedResumes {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String email;
    public byte[] imageData;
    public String path;

    public DTOSavedResumes(int id, String email, byte[] imageData, String path) {
        this.id = id;
        this.email = email;
        this.imageData = imageData;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
