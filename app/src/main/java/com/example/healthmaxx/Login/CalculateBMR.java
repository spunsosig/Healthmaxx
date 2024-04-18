package com.example.healthmaxx.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.healthmaxx.MainActivity;
import com.example.healthmaxx.Models.User;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.databinding.FragmentCalculateBMRBinding;

public class CalculateBMR extends Fragment {
    FragmentCalculateBMRBinding binding;
    Double BMR;
    Double calorieGoal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCalculateBMRBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText heightForm = binding.heightForm;
        EditText weightForm = binding.weightForm;
        EditText ageForm = binding.ageForm;
        RadioGroup radioGroup = binding.radioGroup;

        Button submitBtn = binding.submitBtn2;
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BMR", "height: " + heightForm.getText());
                Log.d("BMR", "weight: " + weightForm.getText());
                Log.d("BMR", "age: " + ageForm.getText());

                int weight = Integer.parseInt(weightForm.getText().toString());
                int height = Integer.parseInt(heightForm.getText().toString());
                int age = Integer.parseInt(ageForm.getText().toString());

                RadioButton r = root.findViewById(radioGroup.getCheckedRadioButtonId());
                Log.d("BMR", "gender: " + r.getText());

                // Activity level multiplier calculation
                RadioGroup activityRadioGroup = binding.activityRadioGroup;
                RadioButton r2 = root.findViewById(activityRadioGroup.getCheckedRadioButtonId());

                double activityMultiplier = 1;

                if (r2.getText().equals("Sedentary")){
                    activityMultiplier = 1.2;
                } else if (r2.getText().equals("Lightly Active")){
                    activityMultiplier = 1.375;
                } else if (r2.getText().equals("Moderately Active")){
                    activityMultiplier = 1.55;
                } else if (r2.getText().equals("Very Active")){
                    activityMultiplier = 1.725;
                } else if (r2.getText().equals("Extra Active")){
                    activityMultiplier = 1.9;
                }

                // Mifflin-St Jeor Equation to calculate BMR

                if (r.getText().equals("Male")){
                    BMR = (10 * weight) + (6.25 * height) - (5 * age) + 5;
                    calorieGoal = BMR * activityMultiplier;
                } else {
                    BMR = (10 * weight) + (6.25 * height) - (5 * age) - 161;
                    calorieGoal = BMR * activityMultiplier;

                }

                Log.d("BMR", "BMR: " + BMR);
                Log.d("calorieGoal", "Calories: " + calorieGoal);

                User user = UserManager.getInstance().getCurrentUser();
                user.setBMR(BMR);
                user.setCalorieGoal(calorieGoal);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}