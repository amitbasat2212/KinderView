package com.example.kinderview.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.kinderview.model.Post;

import java.util.List;

public class PostViewModel extends ViewModel {

    private List<Post> data;

    public List<Post> getData() {
        return data;
    }

}
