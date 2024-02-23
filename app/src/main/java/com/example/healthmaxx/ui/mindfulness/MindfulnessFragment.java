package com.example.healthmaxx.ui.mindfulness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthmaxx.databinding.FragmentMindfulnessBinding;

public class MindfulnessFragment extends Fragment {

    private FragmentMindfulnessBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MindfulnessViewModel mindfulnessViewModel =
                new ViewModelProvider(this).get(MindfulnessViewModel.class);

        binding = FragmentMindfulnessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMindfulness;
        mindfulnessViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}