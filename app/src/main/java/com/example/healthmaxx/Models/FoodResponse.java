package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodResponse {
    @SerializedName("foods")
    private List<Food> foods;

    public List<Food> getFoods() {
        return foods;
    }
}
