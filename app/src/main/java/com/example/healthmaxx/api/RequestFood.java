package com.example.healthmaxx.api;

import com.example.healthmaxx.Models.Food;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RequestFood {

    @GET("nutrition")
    Call<Food> getNutrition(@Header("api_key") String apiKey);

}
