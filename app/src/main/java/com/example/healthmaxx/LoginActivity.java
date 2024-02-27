package com.example.healthmaxx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.healthmaxx.Login.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Example for replacing with RegisterFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new RegisterFragment());
        transaction.commit();
    }

    public boolean checkPasswordsMatch(String pass1, String pass2){
        return pass1.equals(pass2);
    }
}