package com.example.healthmaxx.Models;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String email;
    private String name;
    private String password;
    private Double BMR = 1600.0;
    private Double calorieGoal = 2000.0;
    private Double calorieProgress = 0.0;


    public User(int id, String email, String name, String password){
        this.userId = id;
        this.email = email;
        this.name = name;
        this.password = password;
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

    public Double getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(Double calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public Double getBMR() {
        return BMR;
    }

    public void setBMR(Double BMR) {
        this.BMR = BMR;
    }

    public Double getCalorieProgress() {
        return calorieProgress;
    }

    public void setCalorieProgress(Double calorieProgress) {
        this.calorieProgress = calorieProgress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
