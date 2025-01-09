package model;

import java.util.Date;

public class UserDocument {
    private int id;
    private String ktpNumber;
    private String kkNumber;
    private byte[] ktpImage;
    private byte[] kkImage;
    private byte[] profileImage;
    private String fullName;
    private String address;
    private Date birthDate;
    private String bankAccount;
    private String eWallet;
    private Date createdAt;
    private Date updatedAt;

    // Default constructor
    public UserDocument() {}

    // Getters
    public int getId() {
        return id;
    }

    public String getKtpNumber() {
        return ktpNumber;
    }

    public String getKkNumber() {
        return kkNumber;
    }

    public byte[] getKtpImage() {
        return ktpImage;
    }

    public byte[] getKkImage() {
        return kkImage;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public String getEWallet() {
        return eWallet;
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

    public void setKtpNumber(String ktpNumber) {
        this.ktpNumber = ktpNumber != null ? ktpNumber.trim() : null;
    }

    public void setKkNumber(String kkNumber) {
        this.kkNumber = kkNumber != null ? kkNumber.trim() : null;
    }

    public void setKtpImage(byte[] ktpImage) {
        this.ktpImage = ktpImage;
    }

    public void setKkImage(byte[] kkImage) {
        this.kkImage = kkImage;
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

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount != null ? bankAccount.trim() : null;
    }

    public void setEWallet(String eWallet) {
        this.eWallet = eWallet != null ? eWallet.trim() : null;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Validasi data
    public boolean isValid() {
        return ktpNumber != null && !ktpNumber.trim().isEmpty() &&
                kkNumber != null && !kkNumber.trim().isEmpty() &&
                fullName != null && !fullName.trim().isEmpty() &&
                address != null && !address.trim().isEmpty() &&
                birthDate != null;
    }
}