package com.example.healthmaxx.Models;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String email;

    public User(int id, String email){
        this.userId = id;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
