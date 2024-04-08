package com.example.healthmaxx.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmaxx.DB.DBHandler;
import com.example.healthmaxx.LoginActivity;
import com.example.healthmaxx.MainActivity;
import com.example.healthmaxx.Models.User;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.R;
import com.example.healthmaxx.databinding.FragmentHomeBinding;
import com.example.healthmaxx.databinding.FragmentLoginBinding;
import com.example.healthmaxx.databinding.FragmentRegisterBinding;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText emailEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        passwordEditText = binding.passwordForm;
        confirmPasswordEditText = binding.passwordForm2;
        emailEditText = binding.emailForm;

        TextView loginLink = binding.loginLink;
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("REGFRAG", "Login link clicked");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainer, new LoginFragment()).commit();

            }
        });

        Button submitBtn = binding.button;
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        return root;
    }

    public void addUser(){

        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        if (isSecure(password, confirmPassword, email)){
            DBHandler db = new DBHandler(getContext());
            db.addUser(email, password);

            User user = db.getUser(email);
            UserManager.getInstance().setCurrentUser(user);

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

    }

    public boolean isSecure(String password, String confirmPassword, String email){
        if(((LoginActivity)requireActivity()).isValidInput(password, confirmPassword, email)) {
            if (((LoginActivity) requireActivity()).isValidEmail(email)) {
                if (((LoginActivity) requireActivity()).checkPasswordsMatch(password, confirmPassword)){
                    if (((LoginActivity)requireActivity()).isSecurePassword(password) && ((LoginActivity)requireActivity()).isSecurePassword(confirmPassword)) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    } else {
                        Toast.makeText(getContext(), "Passwords must be a minimum of 8 characters and contain at least one digit and capital letter", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Passwords must match!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter a valid email!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Fields must not be empty!", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}