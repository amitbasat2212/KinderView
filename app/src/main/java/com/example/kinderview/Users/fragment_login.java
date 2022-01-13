package com.example.kinderview.Users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kinderview.R;
import com.example.kinderview.feed.MainActivity;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;


public class fragment_login extends Fragment {

    EditText email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login_fregment, container, false);
        Button loginbutton = view.findViewById(R.id.login_login_btn);
        Button signinbutton = view.findViewById(R.id.login_signup_btn3);

        email = view.findViewById(R.id.login_email_et);
        password = view.findViewById(R.id.login_password_et);


        loginbutton.setOnClickListener(v -> {
            Login();
        });

        signinbutton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_login_to_fragment_sign_in);
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

        Model.instance.Login(email1, password1, email -> {
            if (email != null){
                toFeedActivity();
            }
            else {
                Toast.makeText(getContext(), "User Existed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}