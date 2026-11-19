package model;

import enums.Gender;

public class User {
    private int userId;
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String pincode;
    private static int cnt = 0;
    public User(String name, Gender gender, String phoneNumber, String pincode) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.pincode = pincode;
        userId = ++cnt;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPincode() {
        return pincode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
