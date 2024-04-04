package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

public class LabelNutrients {
    @SerializedName("calories")
    private float calories;

    @SerializedName("fat")
    private float fat;

    @SerializedName("saturatedFat")
    private float saturatedFat;

    @SerializedName("transFat")
    private float transFat;

    @SerializedName("cholesterol")
    private float cholesterol;

    @SerializedName("sodium")
    private float sodium;

    @SerializedName("carbohydrates")
    private float carbohydrates;

    @SerializedName("fiber")
    private float fiber;

    @SerializedName("sugars")
    private float sugars;

    @SerializedName("protein")
    private float protein;

    @SerializedName("calcium")
    private float calcium;

    @SerializedName("iron")
    private float iron;

    public float getCalories() {
        return calories;
    }

    public float getFat() {
        return fat;
    }

    public float getSaturatedFat() {
        return saturatedFat;
    }

    public float getTransFat() {
        return transFat;
    }

    public float getCholesterol() {
        return cholesterol;
    }

    public float getSodium() {
        return sodium;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public float getFiber() {
        return fiber;
    }

    public float getSugars() {
        return sugars;
    }

    public float getProtein() {
        return protein;
    }

    public float getCalcium() {
        return calcium;
    }

    public float getIron() {
        return iron;
    }
}
