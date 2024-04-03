package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;

public class Food {
    @SerializedName("name")
    private String name;

    @SerializedName("calories")
    private float calories;

    @SerializedName("serving_size_g")
    private float serving_size_g;

    @SerializedName("fat_total_g")
    private float fat_total_g;

    @SerializedName("fat_saturated_g")
    private float fat_saturated_g;

    @SerializedName("protein_g")
    private float protein_g;

    @SerializedName("sodium_mg")
    private float sodium_mg;

    @SerializedName("potassium_mg")
    private float potassium_mg;

    @SerializedName("cholesterol_mg")
    private float cholesterol_mg;

    @SerializedName("carbohydrates_total_g")
    private float carbohydrates_total_g;

    @SerializedName("fiber_g")
    private float fiber_g;

    @SerializedName("sugar_g")
    private float sugar_g;

    public Food(String name, Float calories){
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public float getCalories() {
        return calories;
    }

    public float getServing_size_g() {
        return serving_size_g;
    }

    public float getFat_total_g() {
        return fat_total_g;
    }

    public float getFat_saturated_g() {
        return fat_saturated_g;
    }

    public float getProtein_g() {
        return protein_g;
    }

    public float getSodium_mg() {
        return sodium_mg;
    }

    public float getPotassium_mg() {
        return potassium_mg;
    }

    public float getCholesterol_mg() {
        return cholesterol_mg;
    }

    public float getCarbohydrates_total_g() {
        return carbohydrates_total_g;
    }

    public float getFiber_g() {
        return fiber_g;
    }

    public float getSugar_g() {
        return sugar_g;
    }
}

