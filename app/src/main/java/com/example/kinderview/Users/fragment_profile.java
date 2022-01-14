package com.example.kinderview.Users;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kinderview.R;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Profile;

import org.w3c.dom.Text;


public class fragment_profile extends Fragment {

   View view;
   ImageView profile_image;
   TextView numberOfPosts,nameProfile,EmailProfile,PhoneProfile,AdressProfile;
   CheckBox eductor,parent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile, container, false);

        profile_image=view.findViewById(R.id.profile_image);
        nameProfile=view.findViewById(R.id.fragment_profile_name);
        PhoneProfile=view.findViewById(R.id.fragment_profile_phone);
        EmailProfile=view.findViewById(R.id.fragment_profile_email);
        AdressProfile=view.findViewById(R.id.fragment_profile_Adress);
        eductor = view.findViewById(R.id.fragment_profile_eductor);
        parent=view.findViewById(R.id.fragment_profile_parent);

        String name1 = getArguments().getString("name");
        String email1 = getArguments().getString("email");
        String address1 = getArguments().getString("address");
        String phone1 = getArguments().getString("phone");
        boolean eductor1 = getArguments().getBoolean("educator");
        boolean parent1 = getArguments().getBoolean("parent");

        nameProfile.setText(name1);
        PhoneProfile.setText(phone1);
        AdressProfile.setText(address1);
        EmailProfile.setText(email1);
        eductor.setChecked(eductor1);
        parent.setChecked(parent1);


        return view;
    }
}