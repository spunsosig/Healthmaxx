package com.example.healthmaxx.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthmaxx.R;
import com.example.healthmaxx.databinding.FragmentProfileBinding;
import com.example.healthmaxx.databinding.FragmentProgressBinding;
import com.example.healthmaxx.ui.progress.ProgressViewModel;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DonutProgressView weightDonut = binding.donutView;

        weightDonut.addAmount("weight", 0.2f, Color.parseColor("#FB1D32"));

//        final TextView textView = binding.textProfile;
//        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}