package com.example.healthmaxx.Models;

import com.example.healthmaxx.Models.LabelNutrients;
import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("description")
    private String description;

    @SerializedName("labelNutrients")
    private com.example.healthmaxx.Models.LabelNutrients labelNutrients;

    public String getDescription() {
        return description;
    }

    public LabelNutrients getLabelNutrients() {
        return labelNutrients;
    }
}
