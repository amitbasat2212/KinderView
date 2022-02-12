package com.example.kinderview.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;

public class CreatePostViewModel extends ViewModel {

    MutableLiveData<Post> data;

    public CreatePostViewModel(){
        data = new MutableLiveData<>();
    }
    public MutableLiveData<Post> addPost(Post post, Model.AddEditDeleteProfileAndPost listener) {

        data.setValue(post);
        Model.instance.addPost(data.getValue(),listener);
        return data;
    }


}
