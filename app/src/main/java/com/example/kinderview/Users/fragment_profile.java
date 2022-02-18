package com.example.kinderview.Users;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kinderview.R;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Profile;
import com.example.kinderview.viewModel.ProfileViewModel;
import com.squareup.picasso.Picasso;

public class fragment_profile extends Fragment {

    View view;
    ImageView profile_image;
    TextView nameProfile,EmailProfile,PhoneProfile,AdressProfile;
    CheckBox eductor,parent;
    ProfileViewModel profileViewModel;
    Button edit;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile, container, false);
        nameProfile=view.findViewById(R.id.fragment_profile_name);
        PhoneProfile=view.findViewById(R.id.fragment_profile_phone);
        EmailProfile=view.findViewById(R.id.fragment_profile_email);
        AdressProfile=view.findViewById(R.id.fragment_profile_Adress);
        eductor = view.findViewById(R.id.fragment_profile_eductor);
        parent=view.findViewById(R.id.fragment_profile_parent);
        profile_image = view.findViewById(R.id.fragment_profile_image);
        edit = view.findViewById(R.id.fragment_profile_edituser);


        profileViewModel.GetUserconnect(new Model.connect() {
            @Override
            public void onComplete(Profile profile) {
                nameProfile.setText(profile.getName());
                PhoneProfile.setText(profile.getPhone());
                AdressProfile.setText(profile.getAddress());
                EmailProfile.setText(profile.getEmail());
                eductor.setChecked(profile.getEducator());
                parent.setChecked(profile.getParent());
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(profile.getUrlImage().equals("0")){
                            profile.setUrlImage("0");
                        }
                        Navigation.findNavController(view).navigate(fragment_profileDirections.actionFragmentProfileToEditUser(profile.getName(),profile.getParent(),
                                profile.getEducator(), profile.getPhone(),profile.getAddress(),profile.getEmail(),profile.getPassword(), profile.getUrlImage()));
                    }

                });
                if(profile.getUrlImage()==null){
                    profile.setUrlImage("0");
                }
                if (!profile.getUrlImage().equals("0")) {
                    Picasso.get().load(profile.getUrlImage()).into(profile_image);
                }
                else{
                    Picasso.get().load(R.drawable.profile).into(profile_image);
                }

            }
        });


        return view;
    }
}