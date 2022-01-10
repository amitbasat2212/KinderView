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
import android.widget.Toast;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;
import com.example.kinderview.viewModel.CreatePostViewModel;

import java.io.InputStream;

import javax.xml.transform.Result;


public class CreatePost extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PIC = 2;

    EditText date, text;
    ImageView imagePost;
    Button createPost, cancelBtn;
    ProgressBar progressBar;
    View view;
    CreatePostViewModel createPostViewModel;
    ImageButton camBtn;
    ImageButton galleryBtn;
    Bitmap imageBitmap;


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
        text = view.findViewById(R.id.fragment_create_status);

        createPost = view.findViewById(R.id.fragment_create_editbutton);
        cancelBtn = view.findViewById(R.id.fragment_create_cancel);

        camBtn = view.findViewById(R.id.Fragment_create_camra);
        galleryBtn = view.findViewById(R.id.Fragment_create_gallery);
        imagePost = view.findViewById(R.id.fragment_image_post);


        progressBar = view.findViewById(R.id.fragment_create_progressbar);
        progressBar.setVisibility(View.GONE);

        createPost.setOnClickListener(v -> save());

        camBtn.setOnClickListener(v -> {
            openCam();
        });

        galleryBtn.setOnClickListener(v -> {
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
                imagePost.setImageBitmap(imageBitmap);
            }
        }else if(requestCode==REQUEST_IMAGE_PIC){
            if(resultCode==RESULT_OK){
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    imagePost.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void save() {
        progressBar.setVisibility(View.VISIBLE);
        createPost.setEnabled(false);
        cancelBtn.setEnabled(false);

        String id = "99999999999";
        String status = text.getText().toString();

        // TODO: 1/9/2022 to take from the login the user name
        String username ="Yossi";
        String date_post = date.getText().toString();
        String likes = "0";


        Post post = new Post(id, status, username, date_post, likes);
        if(imageBitmap!=null){
            Model.instance.saveImage(imageBitmap, id + ".jpg", url -> {
               post.setImagePostUrl(url);
                createPostViewModel.addPost(post,()->
                {
                    Navigation.findNavController(view).navigate(R.id.action_createPost_to_home_page23);
                });
            });

        }else{
            createPostViewModel.addPost(post,()->
            {
                Navigation.findNavController(view).navigate(R.id.action_createPost_to_home_page23);
            });
        }






    }
}