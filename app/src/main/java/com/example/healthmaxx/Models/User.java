package com.example.healthmaxx.Models;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String email;
    private String name;


    public User(int id, String email, String name){
        this.userId = id;
        this.email = email;
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
