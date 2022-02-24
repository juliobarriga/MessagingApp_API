package com.example.messaging_api.model.Request;

public class LoginRequest {

    private String phoneNumber;
    private String userName;
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
