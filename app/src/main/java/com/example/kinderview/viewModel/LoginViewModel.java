package com.example.kinderview.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;
import com.example.kinderview.model.Post;
import com.example.kinderview.model.Profile;

import java.util.List;

public class LoginViewModel extends ViewModel {

    MutableLiveData<Profile> data;

    public LoginViewModel(){
        data = new MutableLiveData<>();
    }
    public MutableLiveData<Profile> Login(Profile profile, ModelFireBase.sighup listener) {

        data.setValue(profile);
        Model.instance.Login(data.getValue().getEmail(),data.getValue().getPassword(),listener);
        return data;
    }

}
