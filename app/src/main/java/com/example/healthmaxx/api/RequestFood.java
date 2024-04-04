package com.example.healthmaxx.api;

import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.Models.FoodResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RequestFood {

    @GET("nutrition")
    Call<FoodResponse> getNutrition(@Header("X-Api-Key") String apiKey, @Query("query") String query);


}
