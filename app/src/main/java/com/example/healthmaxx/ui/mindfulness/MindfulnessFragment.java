package com.example.healthmaxx.ui.mindfulness;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthmaxx.R;
import com.example.healthmaxx.databinding.FragmentMindfulnessBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MindfulnessFragment extends Fragment {

    View circle;
    Boolean started = false;
    private FragmentMindfulnessBinding binding;
    TextView timerText;
    CountDownTimer timer;
    long time = 60000;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MindfulnessViewModel mindfulnessViewModel =
                new ViewModelProvider(this).get(MindfulnessViewModel.class);

        binding = FragmentMindfulnessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView titleText = binding.titleTextView;
        titleText.setText("Breathing timer");

        timerText = binding.timerTextView;
        timerText.setText("01:00");

        Button startStopBtn = binding.startStopBtn;
        startStopBtn.setText("Start");

        Button resetBtn = binding.resetBtn;
        resetBtn.setText("Reset");

        Animation expandAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.circle_expand);
        Animation contractAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.circle_contract);

        ScaleAnimation textExpandAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        textExpandAnimation.setDuration(5000);

        ScaleAnimation textContractAnimation = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        textContractAnimation.setDuration(5000);

        circle = binding.circle;
        circle.setVisibility(View.INVISIBLE);

        TextView circleText = binding.circleText;
        circleText.setText("");

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerText.setText("01:00");
                cancelTimer();
                started = false;
            }
        });

        startStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                started = !started;

                if (started) {
                    startStopBtn.setText("Stop");
                    startTimer();
                    circle.startAnimation(expandAnimation);
                    circleText.startAnimation(textExpandAnimation);

                    expandAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            circle.setVisibility(View.VISIBLE);
                            circleText.setText("Inhale...");
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            circle.startAnimation(contractAnimation);
                            circleText.startAnimation(textContractAnimation);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    contractAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            circle.setVisibility(View.VISIBLE);
                            circleText.setText("Exhale...");

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            circle.startAnimation(expandAnimation);
                            circleText.startAnimation(textExpandAnimation);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });


                } else {
                    startStopBtn.setText("Start");
                    cancelTimer();
                    circle.setVisibility(View.INVISIBLE);
                }
            }
        });

        return root;
    }

    private void startTimer() {
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                timerText.setText(f.format(min) + ":" + f.format(sec));
            }

            @Override
            public void onFinish() {
                timerText.setText("00:00");
            }
        }.start();
    }

    public void cancelTimer(){
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        cancelTimer();
    }
}