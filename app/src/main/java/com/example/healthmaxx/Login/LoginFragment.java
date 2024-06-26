package com.example.healthmaxx.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmaxx.DB.DBHandler;
import com.example.healthmaxx.MainActivity;
import com.example.healthmaxx.Models.User;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.R;
import com.example.healthmaxx.databinding.FragmentLoginBinding;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView registerLink = binding.registerLink;
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.loginFragmentContainer, new RegisterFragment()).commit();
            }
        });

        Button submitBtn = binding.submitBtn2;
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.emailForm.getText().toString();
                String password = binding.passwordForm.getText().toString();

                DBHandler myDb = new DBHandler(getContext());

                if (myDb.isLogin(email, password)) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    User user = myDb.getUser(email);
                    UserManager.getInstance().setCurrentUser(user);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Email or password incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}