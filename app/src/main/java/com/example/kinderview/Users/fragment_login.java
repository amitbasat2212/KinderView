package com.example.kinderview.Users;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kinderview.R;
import com.example.kinderview.feed.MainActivity;


public class fragment_login extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login_fregment, container, false);
        Button loginbutton = view.findViewById(R.id.login_login_btn);
        Button signinbutton = view.findViewById(R.id.login_signup_btn3);

        loginbutton.setOnClickListener(v -> {
            toFeedActivity();
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


}