package com.example.kinderview;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;
import com.example.kinderview.viewModel.CreatePostViewModel;
import com.example.kinderview.viewModel.EditViewModel;
import com.squareup.picasso.Picasso;

import java.io.InputStream;


public class fragment_edit_post extends Fragment {
    private static final int REQUEST_IMAGE_PIC = 2;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    View view;
    TextView edit_username;
    EditText edit_date, edit_status;
    ProgressBar progressBar;
    EditViewModel editViewModel;
    Button editButton, cancelButton;
    ImageButton camrabtn,gallerybtn;
    String id, status,username,date_post,likes,urlImage;
    ImageView postImage;
    Bitmap imageBitmap;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        edit_username = view.findViewById(R.id.fragment_edit_name);
        edit_date = view.findViewById(R.id.fragment_edit1_date);
        edit_status = view.findViewById(R.id.fragment_edit_status);

        editButton = view.findViewById(R.id.fragment_edit_editbutton);
        cancelButton = view.findViewById(R.id.fragment_edit_cancel);

        camrabtn = view.findViewById(R.id.fragment_edit_camra);
        postImage=view.findViewById(R.id.fragment_edit_imageView);
        gallerybtn= view.findViewById(R.id.fragment_edit_gallery);


        progressBar = view.findViewById(R.id.fragment_edit_progressbar);
        progressBar.setVisibility(View.GONE);

         id = fragment_edit_postArgs.fromBundle(getArguments()).getId();
         status = fragment_edit_postArgs.fromBundle(getArguments()).getStatus();
         username = fragment_edit_postArgs.fromBundle(getArguments()).getUsername();
         date_post = fragment_edit_postArgs.fromBundle(getArguments()).getDate();
         likes = fragment_edit_postArgs.fromBundle(getArguments()).getLikes();
        urlImage = fragment_edit_postArgs.fromBundle(getArguments()).getUrlpostedit();


        edit_username.setText(username);
        edit_date.setText(date_post);
        edit_status.setText(status);
        if (urlImage != null) {
            Picasso.get().load(urlImage).into(postImage);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        camrabtn.setOnClickListener(v -> {
            openCam();
        });

        gallerybtn.setOnClickListener(v -> {
            openGallery();
        });


        return view;
    }

    public void openCam() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }

    public void openGallery() {
        Intent photoPicerIntent = new Intent(Intent.ACTION_PICK);
        photoPicerIntent.setType("image/*");
        startActivityForResult(photoPicerIntent,REQUEST_IMAGE_PIC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                postImage.setImageBitmap(imageBitmap);
            }
        }else if(requestCode==REQUEST_IMAGE_PIC) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    postImage.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }

    }



    private void edit() {
        progressBar.setVisibility(View.VISIBLE);

        editButton.setEnabled(false);
        cancelButton.setEnabled(false);

        String id1 =id;
        String status1 = edit_status.getText().toString();
        String username1 = username;
        String date_post1 = edit_date.getText().toString();
        String likes1 = likes;

        Post post = new Post(id1, status1, username1, date_post1, likes1);

        if(imageBitmap!=null){
            Model.instance.saveImage(imageBitmap, id + ".jpg", url -> {
                post.setImagePostUrl(url);
                editViewModel.editPost(post,()->
                {
                    Navigation.findNavController(view).navigate(R.id.action_fragment_edit_post_to_home_page2);
                });
            });

        }else{
            editViewModel.editPost(post,()->
            {
                Navigation.findNavController(view).navigate(R.id.action_fragment_edit_post_to_home_page2);
            });
        }




    }

}