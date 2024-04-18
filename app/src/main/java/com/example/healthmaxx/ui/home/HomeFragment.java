package com.example.healthmaxx.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthmaxx.DB.DBHandler;
import com.example.healthmaxx.Models.User;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.databinding.FragmentHomeBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    boolean toggle = false;
    Double calorieGoal;
    Double calorieProgress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;

        String greeting;

        DBHandler db = new DBHandler(this.getContext());

        if (UserManager.getInstance().isLoggedIn()){
            textView.setText("Hello, " + UserManager.getInstance().getCurrentUser().getName() + "!");
        } else {
            textView.setText("Hello, guest! ");

        }

        CircularProgressIndicator calorieProgressIndicator = binding.calorieProgressIndicator;
        calorieProgressIndicator.setIndicatorDirection(CircularProgressIndicator.INDICATOR_DIRECTION_COUNTERCLOCKWISE);

        CircularProgressIndicator stepProgressIndicator = binding.stepProgressIndicator;
        stepProgressIndicator.setIndicatorDirection(CircularProgressIndicator.INDICATOR_DIRECTION_COUNTERCLOCKWISE);

        User user = UserManager.getInstance().getCurrentUser();

        if (user != null){
            calorieGoal = user.getCalorieGoal();
            calorieProgress = user.getCalorieProgress();
        }

        int stepGoal = 6000;
        int stepProgress = 1300;

        calorieProgressIndicator.setMax(calorieGoal.intValue());
        calorieProgressIndicator.setProgress(calorieProgress.intValue(),true);

        stepProgressIndicator.setMax(stepGoal);
        stepProgressIndicator.setProgress(stepProgress, true);

        TextView calorieText = binding.calorieCount;
        calorieText.setText(String.format("%s / %s", calorieProgress, calorieGoal));

        TextView stepText = binding.stepCount;
        stepText.setText(String.format("%s / %s", stepProgress, stepGoal));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}