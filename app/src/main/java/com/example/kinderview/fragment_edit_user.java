package com.example.kinderview;

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

import com.example.kinderview.R;
import com.example.kinderview.feed.fragment_edit_postArgs;
import com.example.kinderview.feed.fragment_homeDirections;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;
import com.example.kinderview.model.Post;
import com.example.kinderview.model.Profile;
import com.squareup.picasso.Picasso;

public class fragment_edit_user extends Fragment {

    EditText email, password,name, phone, address;
    CheckBox isEducator, isParent;
    Button edit, deleteuser;
    ProgressBar progressBar;
    String email2, password2, name2, phone2, address2;
    Boolean isEducator2, isPaernt2;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        email = view.findViewById(R.id.edituser_email);
        password = view.findViewById(R.id.edituser_password);
        name = view.findViewById(R.id.edituser_name);
        phone = view.findViewById(R.id.edituser_phone);
        address = view.findViewById(R.id.edituser_address);
        isEducator = view.findViewById(R.id.edituser_isEducator);
        isParent = view.findViewById(R.id.edituser_isParent);

        edit = view.findViewById(R.id.edituser_edit);
        deleteuser = view.findViewById(R.id.edituser_deleteuser);

        progressBar = view.findViewById(R.id.fragment_edituser_progressbar);
        progressBar.setVisibility(View.GONE);

        email2 = fragment_edit_userArgs.fromBundle(getArguments()).getEmail();
        name2 = fragment_edit_userArgs.fromBundle(getArguments()).getName();
        phone2 =  fragment_edit_userArgs.fromBundle(getArguments()).getPhone();
        address2 =  fragment_edit_userArgs.fromBundle(getArguments()).getAddress();
        isEducator2 =  fragment_edit_userArgs.fromBundle(getArguments()).getIsEducator();
        isPaernt2 =  fragment_edit_userArgs.fromBundle(getArguments()).getIsParent();
        password2 =  fragment_edit_userArgs.fromBundle(getArguments()).getPassword();

        email.setText(email2);
        name.setText(name2);
        phone.setText(phone2);
        address.setText(address2);
        isEducator.setChecked(isEducator2);
        isParent.setChecked(isPaernt2);
        password.setText(password2);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        email.setEnabled(false);

        // Inflate the layout for this fragment
        return view;
    }

    private void edit(){
        progressBar.setVisibility(View.VISIBLE);

        edit.setEnabled(false);
        deleteuser.setEnabled(false);

        String address3 =address.getText().toString();
        String email3 = email.getText().toString();
        String name3 = name.getText().toString();
        String phone3 = phone.getText().toString();
        Boolean educated3 = isEducator.isChecked();
        Boolean parent3 = isParent.isChecked();
        String password3 = password.getText().toString();

        Profile profile = new Profile(name3, address3,email3,password3, educated3, parent3, phone3);



        Navigation.findNavController(view).navigate(R.id.action_edit_user_to_fragment_profile);

    }

}