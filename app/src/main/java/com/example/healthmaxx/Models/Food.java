package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Food {

    public Food(int fdcId, String description){
        this.fdcId = fdcId;
        this.description = description;
    }

    @SerializedName(("fdcId"))
    private int fdcId;

    @SerializedName("description")
    private String description;

    @SerializedName("foodNutrients")
    private List<FoodNutrient> foodNutrients;

    private float servingSize;

    public float getServingSize() {
        return servingSize;
    }

    public void setServingSize(float servingSize) {
        this.servingSize = servingSize;
    }

    public String getDescription() {
        return description;
    }

    public int getFdcId() {
        return fdcId;
    }

    public List<FoodNutrient> getFoodNutrients() {
        return foodNutrients;
    }

    public FoodNutrient getFoodNutrientByName(String nutrientName) {
        List<FoodNutrient> foodNutrients = this.foodNutrients;

        for (FoodNutrient nutrient : foodNutrients) {
            if (nutrient.getName().equalsIgnoreCase(nutrientName)) {
                return nutrient;
            }
        }

        // Nutrient not found
        return null;
    }

}
