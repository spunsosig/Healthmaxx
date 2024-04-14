package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

public class FoodNutrient {

    @SerializedName("number")
    private String number;

    @SerializedName("name")
    private String name;

    @SerializedName("amount")
    private float amount;

    @SerializedName("unitName")
    private String unitName;

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public String getUnitName() {
        return unitName;
    }
}
