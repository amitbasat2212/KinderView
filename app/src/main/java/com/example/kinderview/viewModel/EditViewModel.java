package com.example.kinderview.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;

public class EditViewModel extends ViewModel {

    MutableLiveData<Post> data;

    public EditViewModel(){
        data = new MutableLiveData<>();
    }
    public MutableLiveData<Post> editPost(Post post, Model.AddPostListener listener) {

        data.setValue(post);
        Model.instance.editPost(data.getValue(),listener);
        return data;
    }

}
