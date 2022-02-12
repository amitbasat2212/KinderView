package com.example.kinderview.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Profile;


public class ProfileViewModel extends ViewModel {

   MutableLiveData<Profile> data;

    public ProfileViewModel(){
        data = new MutableLiveData<>();
    }
    public LiveData<Profile> GetUserconnect(Model.connect connect) {
        Model.instance.getUserConnect(new Model.connect() {
            @Override
            public void onComplete(Profile profile) {
                data.setValue(profile);
                connect.onComplete(data.getValue());
            }
        });
        return data;
    }

}
