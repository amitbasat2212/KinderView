package com.example.kinderview.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;

import java.util.List;

public class PostViewModel extends ViewModel {

    LiveData<List<Post>> data;

    public PostViewModel(){
        data = Model.instance.getAll();
    }
    public LiveData<List<Post>> getData() {
        return data;
    }




}
