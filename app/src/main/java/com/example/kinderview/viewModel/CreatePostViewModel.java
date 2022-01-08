package com.example.kinderview.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;

import java.lang.invoke.MutableCallSite;
import java.util.List;

public class CreatePostViewModel extends ViewModel {

    MutableLiveData<Post> data;

    public CreatePostViewModel(){
        data = new MutableLiveData<>();
    }
    public MutableLiveData<Post> addPost(Post post, Model.AddPostListener listener) {

        data.setValue(post);
        Model.instance.addPost(data.getValue(),listener);
        return data;
    }


}
