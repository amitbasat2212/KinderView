package com.example.kinderview.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;

import java.util.List;

public class PostViewModel extends ViewModel {

    LiveData<List<Post>> data;
    MutableLiveData<Post> data1;

    public PostViewModel(){
        data = Model.instance.getAll();
        data1 = new MutableLiveData<>();
    }
    public LiveData<List<Post>> getData() {
        return data;
    }

    public MutableLiveData<Post> deletePost(Post post, Model.AddPostListener listener) {
        data1.setValue(post);
        Model.instance.deletePost(data1.getValue(),listener);
        return data1;
    }


}
