package com.example.healthmaxx.ui.foodDiary;

import android.util.Log;
import android.widget.ExpandableListView;

import androidx.lifecycle.ViewModel;

import com.example.healthmaxx.Models.Meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDiaryViewModel extends ViewModel {
    List<String> mealTimes;
    List<Meal> breakfast;
    List<Meal> lunch;
    List<Meal> dinner;
    HashMap<String, List<Meal>> expandableListData = new HashMap<String, List<Meal>>();


    Map<String, List<Meal>> mealMap;

    public FoodDiaryViewModel(){

        Meal oats = new Meal("Oats", 230);
        Meal milk = new Meal("Meal", 150);
        Meal banana = new Meal("Banana",120);
        Meal chickenCurry = new Meal("Chicken Curry", 400);
        Meal rice = new Meal("Rice", 200);

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

    public HashMap<String, List<Meal>> getExpandableListData() {
        return expandableListData;
    }
}


