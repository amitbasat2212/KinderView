package com.example.kinderview.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Profile;

public class LoginViewModel extends ViewModel {

    MutableLiveData<Profile> data;

    public LoginViewModel(){
        data = new MutableLiveData<>();
    }
    public MutableLiveData<Profile> Login(Profile profile, Model.sighup listener) {

        data.setValue(profile);
        Model.instance.Login(data.getValue().getEmail(),data.getValue().getPassword(),listener);
        return data;
    }

}
