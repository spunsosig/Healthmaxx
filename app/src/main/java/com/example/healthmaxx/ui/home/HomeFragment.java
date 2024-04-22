package com.example.healthmaxx.ui.home;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.healthmaxx.DB.DBHandler;
import com.example.healthmaxx.Models.Quote;
import com.example.healthmaxx.Models.User;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.databinding.FragmentHomeBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SensorEventListener {

    private FragmentHomeBinding binding;
    boolean toggle = false;
    Double calorieGoal;
    Double calorieProgress;
    TextView stepText;
    float totalSteps;
    int stepGoal;
    int stepProgress;

    SensorManager sensorManager;
    Sensor stepSensor;
    
    Boolean running = false;


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
            calorieGoal = db.getCalorieGoal(user.getUserId());
            calorieProgress = db.getCalories(user.getUserId());
        } else {
            calorieGoal = 2000.0;
            calorieProgress = 1400.0;
        }

        Log.d("calorie goals home", String.valueOf(calorieGoal));

        calorieProgressIndicator.setMax(calorieGoal.intValue());
        calorieProgressIndicator.setProgress(calorieProgress.intValue(),true);

        stepProgressIndicator.setMax(stepGoal);
        stepProgressIndicator.setProgress(stepProgress, true);

        TextView calorieText = binding.calorieCount;
        calorieText.setText(String.format("%s / %s", calorieProgress, calorieGoal));

        stepGoal = 6000;
        stepProgress = 0;

        stepText = binding.stepCount;
        stepText.setText(String.format("%s / %s", stepProgress, stepGoal));

        Button addCalorieBtn = binding.calorieAddBtn;

        EditText calorieEditText = binding.editCalorie;

        addCalorieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double caloriesAdded = Double.valueOf(calorieEditText.getText().toString());
                calorieProgress += caloriesAdded;

                calorieText.setText(String.format("%s / %s", calorieProgress, calorieGoal));
                calorieProgressIndicator.setProgress(calorieProgress.intValue(),true);
            }
        });

//        List<Quote> quotes = db.getQuotes();

        List<Quote> quotes = new ArrayList<>();

        quotes.add(new Quote("If you don’t find the time, if you don’t do the work, you don’t get the results.", "Arnold Schwarzenegger"));
        quotes.add(new Quote("I’ve failed over and over again in my life and that is why I succeed.", "Michael Jordan"));
        quotes.add(new Quote("If you don’t find the time, if you don’t do the work, you don’t get the results.", "Arnold Schwarzenegger"));
        quotes.add(new Quote("Sometimes, carrying on, just carrying on, is the superhuman achievement.", "Albert Camus"));
        quotes.add(new Quote("The successful warrior is the average man with laser-like focus.", "Bruce Lee"));

        ViewPager2 viewPager = binding.viewPager;
        QuotePagerAdapter adapter = new QuotePagerAdapter(quotes);
        viewPager.setAdapter(adapter);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        
        return root;
    }

    @Override
    public void onResume(){
        Log.d("ONRESUME", "is being called");

        super.onResume();
        running = true;

        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        boolean hasStepDetector = stepSensor != null;

        if (hasStepDetector){
            Log.d("stepdetector", "connected successfully");
        } else {
            Log.d("stepdetector", "not found");
        }

        if (stepSensor != null){
            sensorManager.registerListener(this,  stepSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this.getContext(), "No sensor detected on this device", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0];

            int currentSteps = (int) totalSteps + stepProgress;

            stepProgress = currentSteps;

            stepText.setText(String.format("%s / %s", currentSteps, stepGoal));

            Log.d("STEPCOUNTER", "total: " + String.valueOf(totalSteps));
            Log.d("STEPCOUNTER", "stepProgress: " + String.valueOf(stepProgress));
            Log.d("STEPCOUNTER", "currentSteps: " + String.valueOf(currentSteps));

            CircularProgressIndicator stepProgressIndicator = binding.stepProgressIndicator;
//            stepProgressIndicator.setProgress(currentSteps, true);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    stepProgressIndicator.setMax(6000);
                    stepProgressIndicator.setProgress(currentSteps);
                }
            });

            Log.d("stepProgressindicator", String.valueOf(stepProgressIndicator.getProgress() + " currentSteps " + currentSteps));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}