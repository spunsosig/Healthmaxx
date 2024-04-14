package com.example.healthmaxx.Models;

import com.example.healthmaxx.Models.LabelNutrients;
import com.google.gson.annotations.SerializedName;

public class Food {

    public Food(int fdcId, String description){
        this.fdcId = fdcId;
        this.description = description;
    }

    @SerializedName(("fdcId"))
    private int fdcId;

    @SerializedName("description")
    private String description;

    @SerializedName("labelNutrients")
    private com.example.healthmaxx.Models.LabelNutrients labelNutrients;

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

    public LabelNutrients getLabelNutrients() {
        return labelNutrients;
    }

    public int getFdcId() {
        return fdcId;
    }
}
