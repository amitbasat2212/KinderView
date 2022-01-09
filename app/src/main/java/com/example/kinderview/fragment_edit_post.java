package com.example.kinderview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kinderview.model.Post;
import com.example.kinderview.viewModel.EditViewModel;


public class fragment_edit_post extends Fragment {

    View view;
    TextView edit_username;
    EditText edit_date, edit_status;
    ProgressBar progressBar;
    EditViewModel editViewModel;
    Button editButton, cancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        edit_username = view.findViewById(R.id.fragment_edit_name);
        edit_date = view.findViewById(R.id.fragment_edit1_date);
        edit_status = view.findViewById(R.id.fragment_edit_status);

        editButton = view.findViewById(R.id.fragment_edit_editbutton);
        cancelButton = view.findViewById(R.id.fragment_edit_cancel);

        progressBar = view.findViewById(R.id.fragment_edit_progressbar);
        progressBar.setVisibility(View.GONE);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });


        return view;
    }

    private void edit() {
        progressBar.setVisibility(View.VISIBLE);

        editButton.setEnabled(false);
        cancelButton.setEnabled(false);

        String id = fragment_edit_postArgs.fromBundle(getArguments()).getId();
        String status = fragment_edit_postArgs.fromBundle(getArguments()).getStatus();
        String username = fragment_edit_postArgs.fromBundle(getArguments()).getUsername();
        String date_post = fragment_edit_postArgs.fromBundle(getArguments()).getDate();
        String likes = fragment_edit_postArgs.fromBundle(getArguments()).getLikes();

        edit_username.setText(username);
        edit_date.setText(date_post);
        edit_status.setText(status);


        Post post = new Post(id, status, username, date_post, likes);

        editViewModel.editPost(post,()->
        {
            Navigation.findNavController(view).navigate(R.id.action_fragment_edit_post_to_home_page2);
        });


    }

}