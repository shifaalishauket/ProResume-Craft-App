package com.pro.resume.craft.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profiles")
public class DTOProfile {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String profileId;
    private String firstName;
    private String lastName;
    private String profilePhotoUrl;
    private String email;

    public DTOProfile() {

    }

    public DTOProfile(int id, String profileId, String firstName, String lastName, String profilePhotoUrl, String email) {
        this.id = id;
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
