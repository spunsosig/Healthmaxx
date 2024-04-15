package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

public class Nutrient {
    @SerializedName("value")
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
