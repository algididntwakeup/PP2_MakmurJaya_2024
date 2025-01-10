package model;

import java.util.Date;

public class UserDocument {
    private int id;
    private byte[] profileImage;
    private String fullName;
    private String address;
    private Date birthDate;
    private Date createdAt;
    private Date updatedAt;

    // Default constructor
    public UserDocument() {}

    // Getters
    public int getId() {
        return id;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName != null ? fullName.trim() : null;
    }

    public void setAddress(String address) {
        this.address = address != null ? address.trim() : null;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Validasi data
    public boolean isValid() {
        return fullName != null && !fullName.trim().isEmpty() &&
               address != null && !address.trim().isEmpty() &&
               birthDate != null;
    }
}