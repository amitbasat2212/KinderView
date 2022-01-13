package com.example.kinderview.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kinderview.R;
import com.example.kinderview.feed.MainActivity;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;

public class fragment_sign_in extends Fragment {

    EditText password, email, name, phone, address;
    Button btnlogin;
    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        email = view.findViewById(R.id.signin_email_et);
        password = view.findViewById(R.id.signin_password_et);
        name = view.findViewById(R.id.signin_name);
        phone = view.findViewById(R.id.signin_phone);
        address = view.findViewById(R.id.signin_address);


        btnlogin = view.findViewById(R.id.signin_login_btn);

        btnlogin.setOnClickListener(v -> {
            perforAuth();
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void perforAuth() {
        String password1 = password.getText().toString();
        String email1 = email.getText().toString();
        String phone1 = phone.getText().toString();
        String address1 = address.getText().toString();
        String name1 = name.getText().toString();


        if(!email1.matches(emailPattern))
        {
            Toast.makeText(getContext(), "Email is not correct", Toast.LENGTH_SHORT).show();
        }
        else if(password1.isEmpty() || password1.length()<6){
            Toast.makeText(getContext(), "You can write only 6 char in password", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(getContext(), MainActivity.class);

         Model.instance.sighin(email1, password1, new ModelFireBase.sighup() {
             @Override
             public void onComplete(String email) {
                 startActivity(intent);
                // Navigation.findNavController(view).navigate(R.id.action_fragment_sign_in_to_nav_graph);
             }
         });


    }
}