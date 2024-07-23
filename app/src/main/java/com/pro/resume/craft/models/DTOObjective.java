package com.pro.resume.craft.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Objective")
public class DTOObjective {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String objective;
    private String email;

    // Constructor
    public DTOObjective(int id, String objective, String email) {
        this.id = id;
        this.objective = objective;
        this.email = email;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
