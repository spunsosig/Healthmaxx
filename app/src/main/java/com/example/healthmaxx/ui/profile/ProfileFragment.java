package com.example.healthmaxx.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthmaxx.Models.User;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.R;
import com.example.healthmaxx.databinding.FragmentProfileBinding;
import com.example.healthmaxx.databinding.FragmentProgressBinding;
import com.example.healthmaxx.ui.progress.ProgressViewModel;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private User user;

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

        TextView textView = binding.textView4;

        if (UserManager.getInstance().isLoggedIn()){
            user = UserManager.getInstance().getCurrentUser();
            textView.setText(user.getName());

        }

        Button button = binding.editBtn;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.calculateBMR);
            }
        });

        Button logoutBtn = binding.logoutBtn;

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setMessage("Are you sure you want to exit?");
                builder.setTitle("Alert!");
                builder.setCancelable(false);

                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserManager.getInstance().logout();

                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.loginFragment3);
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}