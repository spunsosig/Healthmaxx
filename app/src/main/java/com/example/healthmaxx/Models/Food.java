package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Food {

    public Food(int fdcId, String description, LabelNutrients labelNutrients){
        this.fdcId = fdcId;
        this.description = description;
        this.labelNutrients = labelNutrients;
    }

    @SerializedName("labelNutrients")
    private LabelNutrients labelNutrients;

    @SerializedName(("fdcId"))
    private int fdcId;

    @SerializedName("description")
    private String description;

    @SerializedName("foodNutrients")
    private List<FoodNutrient> foodNutrients;

    @SerializedName("nutrient")
    private NutrientOld nutrient;

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

    public NutrientOld getNutrient() {
        return nutrient;
    }

    public LabelNutrients getLabelNutrients() {
        return labelNutrients;
    }
}
