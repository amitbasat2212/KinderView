package com.example.kinderview.feed;

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
    Button editButton, cancelButton, deletepic;
    ImageButton camrabtn,gallerybtn;
    String id, status,username,date_post,urlImage;
    ImageView postImage;
    Bitmap imageBitmap;
    Profile profile1;

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
        edit_username.setEnabled(false);

        editButton = view.findViewById(R.id.fragment_edit_editbutton);
        cancelButton = view.findViewById(R.id.fragment_edit_cancel);

        camrabtn = view.findViewById(R.id.fragment_edit_camra);
        postImage=view.findViewById(R.id.fragment_edit_imageView);
        gallerybtn= view.findViewById(R.id.fragment_edit_gallery);
        deletepic = view.findViewById(R.id.fragment_edit_deletepicture);

        progressBar = view.findViewById(R.id.fragment_edit_progressbar);
        progressBar.setVisibility(View.GONE);

        id = fragment_edit_postArgs.fromBundle(getArguments()).getId();
        status = fragment_edit_postArgs.fromBundle(getArguments()).getStatus();
        username = fragment_edit_postArgs.fromBundle(getArguments()).getUsername();
        date_post = fragment_edit_postArgs.fromBundle(getArguments()).getDate();
        urlImage = fragment_edit_postArgs.fromBundle(getArguments()).getUrlpostedit();

        Model.instance.getUserConnect(new ModelFireBase.connect() {
            @Override
            public void onComplete(Profile profile) {
                Model.instance.mainThread.post(new Runnable() {
                    @Override
                    public void run() {

                        if (profile.getUrlImage() != null) {
                            profile1 = profile;

                        }

                    }
                });

            }
        });

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

        deletepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        camrabtn.setOnClickListener(v -> {
            openCam();
        });

        gallerybtn.setOnClickListener(v -> {
            openGallery();
        });

        cancelButton.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_fragment_edit_post_to_home_page));


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
        deletepic.setEnabled(false);
        String id1 =id;
        String status1 = edit_status.getText().toString();
        String username1 = username;
        String date_post1 = edit_date.getText().toString();

        Post post = new Post(id1, status1, username1, date_post1);
        post.setProfilePic(profile1.getUrlImage());

        if(status1.isEmpty() &&imageBitmap==null){
            Toast.makeText(getContext(), "the status or picture is empty", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            editButton.setEnabled(true);
            cancelButton.setEnabled(true);
            return;
        }

        if(imageBitmap!=null){
            Model.instance.saveImage(imageBitmap, id + ".jpg", url -> {
                post.setImagePostUrl(url);
                editViewModel.editPost(post,()->
                {
                    Navigation.findNavController(view).navigate(R.id.action_fragment_edit_post_to_home_page);

                });
            });

        }else{
            post.setImagePostUrl(urlImage);
            editViewModel.editPost(post,()->
            {
                Navigation.findNavController(view).navigate(R.id.action_fragment_edit_post_to_home_page);
            });
        }

    }

    private void delete() {
        progressBar.setVisibility(View.VISIBLE);
        editButton.setEnabled(false);
        cancelButton.setEnabled(false);
        deletepic.setEnabled(false);

        Post post = new Post(id, status, username, date_post);
        post.setUrlImagePost(urlImage);

        if(!post.getUrlImagePost().equals("0")) {
            editViewModel.deletePic(post, id + ".jpg", () -> {
                urlImage="0";
                Navigation.findNavController(view).navigate(fragment_edit_postDirections.actionGlobalFragmentEditPost(username, date_post, status, id, urlImage));
            });

        }
        else
        {
            Toast.makeText(getContext(), "No picture exist", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            editButton.setEnabled(true);
            cancelButton.setEnabled(true);
            deletepic.setEnabled(true);

        }

    }

}