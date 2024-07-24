package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cover")
public class DTOCoverLetter {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String email;
    public byte[] imageData;
    public String path;
    public String result;

    public DTOCoverLetter(int id, String email, byte[] imageData, String path, String result) {
        this.id = id;
        this.email = email;
        this.imageData = imageData;
        this.path = path;
        this.result = result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
