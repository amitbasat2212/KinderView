package com.example.kinderview;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kinderview.R;
import com.example.kinderview.feed.MainActivity;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;


public class fragment_login extends Fragment {

    EditText email, password;
    ProgressBar progressBar;
    Button loginbutton,signinbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login_fregment, container, false);
         loginbutton = view.findViewById(R.id.login_login_btn);
         signinbutton = view.findViewById(R.id.login_signup_btn3);

        email = view.findViewById(R.id.login_email_et);
        password = view.findViewById(R.id.login_password_et);
        progressBar = view.findViewById(R.id.fragment_login_progressbar);
        progressBar.setVisibility(View.GONE);

        loginbutton.setOnClickListener(v -> {
            Login();
        });

        signinbutton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_login_to_fragment_sign_up);
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    //TODO: Connect to the view authentication
    public void Login()
    {
        String password1 = password.getText().toString();
        String email1 = email.getText().toString();


        if (!(password1.isEmpty() || email1.isEmpty() ))
        {
            progressBar.setVisibility(View.VISIBLE);
            loginbutton.setEnabled(false);
            signinbutton.setEnabled(false);
            Model.instance.Login(email1,password1, email -> {
                if (email != null) {
                    toFeedActivity();
                } else {
                    Toast.makeText(getContext(), "User not Existed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "Password or email is empty", Toast.LENGTH_SHORT).show();
            return;
        }

    }

}