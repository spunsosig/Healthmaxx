package com.example.healthmaxx.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> calorieText;
    private final MutableLiveData<String> stepText;

    private int calorieGoal = 2000;
    private int calorieProgress = 1255;
    private int stepGoal = 6000;
    private int stepProgress = 3463;

    public MutableLiveData<String> getCalorieText() {
        return calorieText;
    }

    public MutableLiveData<String> getStepText() {
        return stepText;
    }

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hello Ikram!");

        calorieText = new MutableLiveData<>();
        stepText = new MutableLiveData<>();

        calorieText.setValue(String.format("%s / %s", calorieProgress, calorieGoal));
        stepText.setValue(String.format("%s / %s", stepProgress, stepGoal));

    }

    public LiveData<String> getText() {
        return mText;
    }

}