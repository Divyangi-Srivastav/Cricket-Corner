package com.example.livecricketapp.model;

public class UserHelper {

    String userName, userPhoneno,userEmail, userAddress, userKey ;
    private String status = "user";

    public UserHelper() {}

    public UserHelper(String userName, String userPhoneno, String userEmail, String userAddress, String userKey) {
        this.userName = userName;
        this.userPhoneno = userPhoneno;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userKey = userKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneno() {
        return userPhoneno;
    }

    public void setUserPhoneno(String userPhoneno) {
        this.userPhoneno = userPhoneno;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
