package com.example.kinderview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;
import com.example.kinderview.viewModel.CreatePostViewModel;
import com.example.kinderview.viewModel.PostViewModel;


public class CreatePost extends Fragment {

    EditText date, text;
    ImageView imagePost;
    Button editPicture, createPost, cancelBtn;
    ProgressBar progressBar;
    View view;
    CreatePostViewModel createPostViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        createPostViewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_post,container, false);
        date = view.findViewById(R.id.fragment_create_date);
        text = view.findViewById(R.id.fragment_create_text);
        createPost = view.findViewById(R.id.fragment_create_createbutton);
        cancelBtn = view.findViewById(R.id.fragment_create_cancel);
        editPicture = view.findViewById(R.id.fragment_create_editpicture);

        progressBar = view.findViewById(R.id.fragment_create_progressbar);
        progressBar.setVisibility(View.GONE);

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    return view;
}

    private void save() {
        progressBar.setVisibility(View.VISIBLE);
        createPost.setEnabled(false);
        cancelBtn.setEnabled(false);

        String id = "123";
        String status = text.getText().toString();
        String username ="Yossi";
        String date_post = date.getText().toString();
        String likes = "0";
        String comment = "0";


        Post post = new Post(id, status, username, date_post, likes,comment);
        createPostViewModel.addPost(post, ()->
        {
            Navigation.findNavController(view).navigate(R.id.action_createPost_to_home_page23);
        }
);

    }
}