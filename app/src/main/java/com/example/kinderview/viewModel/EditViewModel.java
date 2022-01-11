package com.example.kinderview.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;

public class EditViewModel extends ViewModel {

    MutableLiveData<Post> data;
    MutableLiveData<Post> data1;

    public EditViewModel(){
        data = new MutableLiveData<>();
        data1 = new MutableLiveData<>();
    }
    public MutableLiveData<Post> editPost(Post post, Model.AddPostListener listener) {

        data.setValue(post);
        Model.instance.editPost(data.getValue(),listener);
        return data;
    }

    public MutableLiveData<Post> deletePic(Post post, String picName, Model.AddPostListener listener) {
        data1.setValue(post);
        Model.instance.deletePic(data1.getValue(), picName ,listener);
        return data1;
    }
}
