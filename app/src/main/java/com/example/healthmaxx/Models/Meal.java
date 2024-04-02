package com.example.healthmaxx.Models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;

import java.util.List;
import java.util.Map;

public class Meal {
    private String name;
    private int calories;

    public Meal(String name, int calories){
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }
}

