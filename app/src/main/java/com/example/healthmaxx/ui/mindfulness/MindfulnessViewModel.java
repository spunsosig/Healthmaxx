package com.example.healthmaxx.ui.mindfulness;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MindfulnessViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MindfulnessViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mindfulness fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}