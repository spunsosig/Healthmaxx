package com.example.healthmaxx.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthmaxx.R;
import com.example.healthmaxx.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    boolean toggle = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        CircularProgressIndicator calorieProgressIndicator = binding.calorieProgressIndicator;
        calorieProgressIndicator.setIndicatorDirection(CircularProgressIndicator.INDICATOR_DIRECTION_COUNTERCLOCKWISE);

        CircularProgressIndicator stepProgressIndicator = binding.stepProgressIndicator;
        stepProgressIndicator.setIndicatorDirection(CircularProgressIndicator.INDICATOR_DIRECTION_COUNTERCLOCKWISE);

        int calorieGoal = 2000;
        int calorieProgress = 1100;
        int stepGoal = 6000;
        int stepProgress = 1300;

        calorieProgressIndicator.setMax(calorieGoal);
        calorieProgressIndicator.setProgress(calorieProgress,true);

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