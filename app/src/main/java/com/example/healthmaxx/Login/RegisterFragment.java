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
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmaxx.MainActivity;
import com.example.healthmaxx.R;
import com.example.healthmaxx.databinding.FragmentHomeBinding;
import com.example.healthmaxx.databinding.FragmentLoginBinding;
import com.example.healthmaxx.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

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

        TextView loginLink = binding.loginLink;
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("REGFRAG", "Login link clicked");
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registerFragment, new LoginFragment()).commit();
//                LoginFragment loginFragment = new LoginFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.registerFragmentContainer, loginFragment);
//                transaction.commit();

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainer, new LoginFragment()).commit();

            }
        });

        Button submitBtn = binding.button;
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "button clicked", Toast.LENGTH_SHORT).show();
                Log.d("REGFRAG", "Submit button clicked");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}