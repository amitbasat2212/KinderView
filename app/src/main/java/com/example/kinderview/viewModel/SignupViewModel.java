package com.example.kinderview.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Profile;

public class SignupViewModel extends ViewModel {

    MutableLiveData<Profile> data;

    public SignupViewModel(){
        data = new MutableLiveData<>();
    }
    public MutableLiveData<Profile> sighup(Profile profile, Model.sighup listener) {
        data.setValue(profile);
        Model.instance.sighup(profile,listener);
        return data;
    }

    public MutableLiveData<Profile> editProfile(Profile profile, Model.AddEditDeleteProfileAndPost listener) {
        data.setValue(profile);
        Model.instance.editprofile(profile,listener);
        return data;
    }
}
