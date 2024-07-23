package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "education")
public class DTOEducation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String degree;
    private String startDate;
    private String endDate;
    private String description;
    private String grade;
    private String university;
    private String email;

    // Constructor
    public DTOEducation(int id, String degree, String startDate, String endDate, String description, String grade, String university, String email) {
        this.id = id;
        this.degree = degree;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.grade = grade;
        this.university = university;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
