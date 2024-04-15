package com.example.healthmaxx.Models;

import java.util.List;

public class FoodNutrientUtils {

    public static NutrientOld findNutrientByName(List<FoodNutrient> foodNutrients, String nutrientName) {
        for (FoodNutrient foodNutrient : foodNutrients) {
            NutrientOld nutrient = foodNutrient.getNutrient();
            if (nutrient != null && nutrientName.equalsIgnoreCase(nutrient.getName())) {
                return nutrient;
            }
        }
        return null;
    }
}
