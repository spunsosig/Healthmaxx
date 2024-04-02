package com.example.healthmaxx;

import static android.app.PendingIntent.getActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.healthmaxx.ui.foodDiary.FoodDiaryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.healthmaxx.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.Fragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;
    private boolean toggle;
    FloatingActionButton addFoodBtn;
    FloatingActionButton addExerciseBtn;
    FloatingActionButton addWeightBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Animation rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        Animation fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_progress, R.id.navigation_mindfulness, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        addFoodBtn = findViewById(R.id.foodbtn);
        addExerciseBtn = findViewById(R.id.exerciseBtn);
        addWeightBtn = findViewById(R.id.weightBtn);

        addFoodBtn.setOnClickListener(this);

        toggle = true;
        FloatingActionButton addBtn = findViewById(R.id.floatingActionButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle = !toggle;

                if (toggle){
                    addFoodBtn.setVisibility(View.VISIBLE);
                    addExerciseBtn.setVisibility(View.VISIBLE);
                    addWeightBtn.setVisibility(View.VISIBLE);
                    addFoodBtn.startAnimation(toBottom);
                    addExerciseBtn.startAnimation(toBottom);
                    addWeightBtn.startAnimation(toBottom);
                    addBtn.startAnimation(rotateClose);
                } else {
                    addFoodBtn.setVisibility(View.INVISIBLE);
                    addExerciseBtn.setVisibility(View.INVISIBLE);
                    addFoodBtn.startAnimation(fromBottom);
                    addExerciseBtn.startAnimation(fromBottom);
                    addWeightBtn.startAnimation(fromBottom);
                    addBtn.startAnimation(rotateOpen);
                }

            }
        });

    }


    @Override
    public void onClick(View v) {
        androidx.fragment.app.Fragment fragment = null;
        Log.d("btnclick", "button clicked");


        if (v == findViewById(R.id.foodbtn)){
            Log.d("btnclick", "Food button clicked");
            fragment = new FoodDiaryFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).commit();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.foodDiaryFragment);
        }
    }
}