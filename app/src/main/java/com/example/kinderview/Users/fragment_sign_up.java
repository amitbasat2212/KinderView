package com.example.kinderview.Users;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kinderview.R;
import com.example.kinderview.feed.MainActivity;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Profile;

public class fragment_sign_up extends Fragment {

    EditText password, email, name, phone, address;
    CheckBox eductor,parent;
    Button btnlogin;
    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressBar progressBar;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        email = view.findViewById(R.id.signin_email_et);
        password = view.findViewById(R.id.signin_password_et);
        name = view.findViewById(R.id.signin_name);
        phone = view.findViewById(R.id.signin_phone);
        address = view.findViewById(R.id.signin_address);
        eductor=view.findViewById(R.id.signin_educator);
        parent=view.findViewById(R.id.signin_parent);

        btnlogin = view.findViewById(R.id.signin_login_btn);
        progressBar = view.findViewById(R.id.fragment_sighup_progressbar);
        progressBar.setVisibility(View.GONE);

        btnlogin.setOnClickListener(v -> {
            perforAuth();
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void perforAuth() {
        String password1 = password.getText().toString();
        String email1 = email.getText().toString();
        String phone1 = phone.getText().toString();
        String address1 = address.getText().toString();
        String name1 = name.getText().toString();
        boolean educator1 = eductor.isChecked();
        boolean parent1 = parent.isChecked();
        progressBar.setVisibility(View.VISIBLE);
        btnlogin.setEnabled(false);

        Profile profile=new Profile(name1,address1,email1,password1,educator1,parent1,phone1);

        if(!email1.matches(emailPattern))
        {
            Toast.makeText(getContext(), "Email is not correct", Toast.LENGTH_SHORT).show();
        }
        else if(password1.isEmpty() || password1.length()<6){
            Toast.makeText(getContext(), "You can write only 6 char in password", Toast.LENGTH_SHORT).show();
        }


         Model.instance.sighin(profile, email -> {
            toFeedActivity();
         });


    }
}