package com.example.healthmaxx.api;

import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.Models.FoodResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RequestFood {

    @GET("foods/search")
    Call<FoodResponse> searchFood(@Header("X-Api-Key") String apiKey, @Query("query") String query);

    @GET("food/{fdcid}")
    Call<Food> findFoodById(@Header("X-Api-Key") String apiKey, @Query("fdcId") int fdcId);
}
