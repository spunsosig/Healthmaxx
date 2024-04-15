package com.example.healthmaxx.Models;

import com.google.gson.annotations.SerializedName;

public class NutrientOld {
    @SerializedName("id")
    private int id;

    @SerializedName("number")
    private String number;

    @SerializedName("name")
    private String name;

    @SerializedName("rank")
    private int rank;

    @SerializedName("unitName")
    private String unitName;

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public String getUnitName() {
        return unitName;
    }
}
