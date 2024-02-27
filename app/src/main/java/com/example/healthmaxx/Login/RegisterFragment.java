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

import com.example.healthmaxx.LoginActivity;
import com.example.healthmaxx.MainActivity;
import com.example.healthmaxx.R;
import com.example.healthmaxx.databinding.FragmentHomeBinding;
import com.example.healthmaxx.databinding.FragmentLoginBinding;
import com.example.healthmaxx.databinding.FragmentRegisterBinding;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

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
                submit();
            }
        });

        return root;
    }

    public void submit(){
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (((LoginActivity) requireActivity()).checkPasswordsMatch(password, confirmPassword)){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Passwords must match!", Toast.LENGTH_LONG).show();
        }
    }
}