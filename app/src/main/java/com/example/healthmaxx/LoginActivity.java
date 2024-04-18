package com.example.healthmaxx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.healthmaxx.Login.LoginFragment;
import com.example.healthmaxx.Login.RegisterFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.text.Regex;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Example for replacing with RegisterFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new LoginFragment());
        transaction.commit();
    }

    public boolean isValidInput(String... inputs){
        for (String input : inputs){
            if (input.isEmpty()){
                return false;
            }
        }
        return true;
    }

    public boolean isSecurePassword(String pass){
        String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);

        return matcher.matches();
    }

    public boolean checkPasswordsMatch(String pass1, String pass2){
        return pass1.equals(pass2);
    }

    public boolean isValidEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}