package com.example.healthmaxx.ui.foodDiary;

import androidx.lifecycle.ViewModel;

import com.example.healthmaxx.Models.Food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDiaryViewModel extends ViewModel {
    List<String> mealTimes;
    List<Food> breakfast;
    List<Food> lunch;
    List<Food> dinner;
    HashMap<String, List<Food>> expandableListData = new HashMap<String, List<Food>>();


    Map<String, List<Food>> mealMap;

    public FoodDiaryViewModel(){

        Food oats = new Food("Oats", 230F);
        Food milk = new Food("Meal", 150F);
        Food banana = new Food("Banana",120F);
        Food chickenCurry = new Food("Chicken Curry", 400F);
        Food rice = new Food("Rice", 200F);

        breakfast = new ArrayList<>();
        breakfast.addAll(Arrays.asList(oats, milk, banana));

        lunch = new ArrayList<>();
        lunch.addAll(Arrays.asList(chickenCurry, rice));

        dinner = new ArrayList<>();
        dinner.addAll(Arrays.asList(chickenCurry, rice));

        expandableListData = new HashMap<>();
        expandableListData.put("Breakfast", breakfast);
        expandableListData.put("Lunch", lunch);
        expandableListData.put("Dinner", dinner);

    }

    public HashMap<String, List<Food>> getExpandableListData() {
        return expandableListData;
    }
}


