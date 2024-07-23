package com.pro.resume.craft.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "experience")
public class DTOExperience {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String company;
    private String title;
    private String startDate;
    private String endDate;
    private String description;
    private String email;
    // Constructor
    public DTOExperience(int id, String company, String title, String startDate, String endDate, String description, String email) {
        this.id = id;
        this.company = company;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
