package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Reference")
public class DTOReference {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String reffName;
    private String reffDesignation;
    private String reffCompanyName;
    private String reffPhone;
    private String email;

    // Constructor
    public DTOReference(int id, String reffName, String reffDesignation, String reffCompanyName, String reffPhone, String email) {
        this.id = id;
        this.reffName = reffName;
        this.reffDesignation = reffDesignation;
        this.reffCompanyName = reffCompanyName;
        this.reffPhone = reffPhone;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReffName() {
        return reffName;
    }

    public void setReffName(String reffName) {
        this.reffName = reffName;
    }

    public String getReffDesignation() {
        return reffDesignation;
    }

    public void setReffDesignation(String reffDesignation) {
        this.reffDesignation = reffDesignation;
    }

    public String getReffCompanyName() {
        return reffCompanyName;
    }

    public void setReffCompanyName(String reffCompanyName) {
        this.reffCompanyName = reffCompanyName;
    }

    public String getReffPhone() {
        return reffPhone;
    }

    public void setReffPhone(String reffPhone) {
        this.reffPhone = reffPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

