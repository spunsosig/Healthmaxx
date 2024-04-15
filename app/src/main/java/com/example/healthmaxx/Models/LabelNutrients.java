package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

public class LabelNutrients {
    @SerializedName("fat")
    private Nutrient fat;

    @SerializedName("saturatedFat")
    private Nutrient saturatedFat;

    @SerializedName("transFat")
    private Nutrient transFat;

    @SerializedName("cholesterol")
    private Nutrient cholesterol;

    @SerializedName("sodium")
    private Nutrient sodium;

    @SerializedName("carbohydrates")
    private Nutrient carbohydrates;

    @SerializedName("fiber")
    private Nutrient fiber;

    @SerializedName("sugars")
    private Nutrient sugars;

    @SerializedName("protein")
    private Nutrient protein;

    @SerializedName("calcium")
    private Nutrient calcium;

    @SerializedName("iron")
    private Nutrient iron;

    @SerializedName("potassium")
    private Nutrient potassium;

    @SerializedName("calories")
    private Nutrient calories;

    // Getters and setters

    public Nutrient getFat() {
        return fat;
    }

    public Nutrient getSaturatedFat() {
        return saturatedFat;
    }

    public Nutrient getTransFat() {
        return transFat;
    }

    public Nutrient getCholesterol() {
        return cholesterol;
    }

    public Nutrient getSodium() {
        return sodium;
    }

    public Nutrient getCarbohydrates() {
        return carbohydrates;
    }

    public Nutrient getFiber() {
        return fiber;
    }

    public Nutrient getSugars() {
        return sugars;
    }

    public Nutrient getProtein() {
        return protein;
    }

    public Nutrient getCalcium() {
        return calcium;
    }

    public Nutrient getIron() {
        return iron;
    }

    public Nutrient getPotassium() {
        return potassium;
    }

    public Nutrient getCalories() {
        return calories;
    }
}

