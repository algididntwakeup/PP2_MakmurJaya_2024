package model;

import java.util.Date;

public class UserDocument {
    // Properties
    private int id;
    private String email;
    private String password;
    private String fullName;
    private String address;
    private Date birthDate;
    private String gender;
    private String phoneNumber;
    private byte[] profileImage;
    private Date createdAt;
    private Date updatedAt;

    // Default constructor
    public UserDocument() {}

    // Getters
    public int getId() { 
        return id; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public String getPassword() { 
        return password; 
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
    
    public String getGender() { 
        return gender; 
    }
    
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    public byte[] getProfileImage() { 
        return profileImage; 
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
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
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
    
    public void setGender(String gender) { 
        this.gender = gender; 
    }
    
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }
    
    public void setProfileImage(byte[] profileImage) { 
        this.profileImage = profileImage; 
    }
    
    public void setCreatedAt(Date createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public void setUpdatedAt(Date updatedAt) { 
        this.updatedAt = updatedAt; 
    }

    // Validation
    public boolean isValid() {
        return fullName != null && !fullName.trim().isEmpty() &&
               address != null && !address.trim().isEmpty() &&
               birthDate != null &&
               gender != null && !gender.trim().isEmpty() &&
               phoneNumber != null && !phoneNumber.trim().isEmpty();
    }
}