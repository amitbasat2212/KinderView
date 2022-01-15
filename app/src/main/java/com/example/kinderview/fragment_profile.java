package com.example.kinderview;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kinderview.R;
import com.example.kinderview.feed.fragment_homeDirections;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;
import com.example.kinderview.model.Profile;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class fragment_profile extends Fragment {

    View view;
    ImageView profile_image;
    TextView nameProfile,EmailProfile,PhoneProfile,AdressProfile;
    CheckBox eductor,parent;
    Button edit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile, container, false);

        // profile_image=view.findViewById(R.id.profile_image);
        nameProfile=view.findViewById(R.id.fragment_profile_name);
        PhoneProfile=view.findViewById(R.id.fragment_profile_phone);
        EmailProfile=view.findViewById(R.id.fragment_profile_email);
        AdressProfile=view.findViewById(R.id.fragment_profile_Adress);
        eductor = view.findViewById(R.id.fragment_profile_eductor);
        parent=view.findViewById(R.id.fragment_profile_parent);
        profile_image = view.findViewById(R.id.fragment_profile_image);
        edit = view.findViewById(R.id.fragment_profile_edituser);


        Model.instance.getUserConnect(new ModelFireBase.connect() {
            @Override
            public void onComplete(Profile profile) {
                nameProfile.setText(profile.getName());
                PhoneProfile.setText(profile.getPhone());
                AdressProfile.setText(profile.getAddress());
                EmailProfile.setText(profile.getEmail());
                eductor.setChecked(profile.getEducator());
                parent.setChecked(profile.getParent());

                Model.instance.mainThread.post(new Runnable() {
                    @Override
                    public void run() {

                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Navigation.findNavController(view).navigate(fragment_profileDirections.actionFragmentProfileToEditUser(profile.getName(),profile.getParent(),
                                        profile.getEducator(), profile.getPhone(),profile.getAddress(),profile.getEmail(),profile.getPassword()));
                            }
                        });

                        if (profile.getUrlImage() != null) {
                            Picasso.get().load(profile.getUrlImage()).into(profile_image);
                        }
                    }
                });


            }
        });


        return view;
    }
}