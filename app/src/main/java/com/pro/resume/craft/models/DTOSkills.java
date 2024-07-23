package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "skills")
public class DTOSkills {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String skill;
    private String level;
    private String email;

    // Constructor
    public DTOSkills(int id, String skill, String level, String email) {
        this.id = id;
        this.skill = skill;
        this.level = level;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

