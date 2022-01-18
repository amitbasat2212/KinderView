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

import com.example.kinderview.R;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;
import com.example.kinderview.model.Post;
import com.example.kinderview.model.Profile;
import com.example.kinderview.viewModel.CreatePostViewModel;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.UUID;

import javax.xml.transform.Result;


public class fragment_create_post extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PIC = 2;

    EditText date, text;

    ImageView imagePost,imageProfil;
    Button createPost, cancelBtn;
    ProgressBar progressBar;
    View view;
    CreatePostViewModel createPostViewModel;
    ImageButton camBtn;
    ImageButton galleryBtn;
    Bitmap imageBitmap;
    TextView name,Email;
    Profile profile1;

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

        createPost = view.findViewById(R.id.fragment_create_createbutton);
        cancelBtn = view.findViewById(R.id.fragment_create_cancel);

        camBtn = view.findViewById(R.id.Fragment_create_camra);
        galleryBtn = view.findViewById(R.id.Fragment_create_gallery);
        imagePost = view.findViewById(R.id.fragment_image_post);
        imageProfil =view.findViewById(R.id.fragment_create_profile);
        name = view.findViewById(R.id.fragment_create_name);

        Model.instance.getUserConnect(new ModelFireBase.connect() {
            @Override
            public void onComplete(Profile profile) {
                Model.instance.mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(profile.getEmail());
                        if (profile.getUrlImage() != null) {
                            profile1 = profile;
                            Picasso.get().load(profile.getUrlImage()).resize(50, 50)
                                    .centerCrop().into(imageProfil);
                        }
                    }
                });

            }
        });


        progressBar = view.findViewById(R.id.fragment_create_progressbar);
        progressBar.setVisibility(View.GONE);

        name.setEnabled(false);

        createPost.setOnClickListener(v -> save());

        camBtn.setOnClickListener(v -> {
            openCam();
        });

        galleryBtn.setOnClickListener(v -> {
            openGallery();
        });

        cancelBtn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_createPost_to_home_page23));


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

    UUID uniqueKey = UUID.randomUUID();

    private void save() {
        progressBar.setVisibility(View.VISIBLE);
        createPost.setEnabled(false);
        cancelBtn.setEnabled(false);

        String id = String.valueOf(uniqueKey);
        String status = text.getText().toString();
        String username = name.getText().toString();
        String date_post = date.getText().toString();
        String likes = "0";


        Post post = new Post(id, status, username, date_post, likes);
        post.setProfilePic(profile1.getUrlImage());
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