package com.example.kinderview;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kinderview.R;
import com.example.kinderview.feed.fragment_edit_postArgs;
import com.example.kinderview.feed.fragment_homeDirections;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;
import com.example.kinderview.model.Post;
import com.example.kinderview.model.Profile;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class fragment_edit_user extends Fragment {

    EditText email, password, name, phone, address;
    CheckBox isEducator, isParent;
    Button edit;
    ProgressBar progressBar;
    String email2, password2, name2, phone2, address2, urlImage;
    Boolean isEducator2, isPaernt2;
    View view;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PIC = 2;
    ImageButton btn_gallery, btn_camera;
    ImageView picImage;
    Bitmap imageBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        email = view.findViewById(R.id.edituser_email);
        password = view.findViewById(R.id.edituser_password);
        name = view.findViewById(R.id.edituser_name);
        phone = view.findViewById(R.id.edituser_phone);
        address = view.findViewById(R.id.edituser_address);
        isEducator = view.findViewById(R.id.edituser_isEducator);
        isParent = view.findViewById(R.id.edituser_isParent);

        edit = view.findViewById(R.id.edituser_edit);
        btn_gallery = view.findViewById(R.id.fragment_editUser_gallery);
        btn_camera = view.findViewById(R.id.fragment_editUser_camra);

        progressBar = view.findViewById(R.id.fragment_edituser_progressbar);
        progressBar.setVisibility(View.GONE);
        picImage = view.findViewById(R.id.edituser_profilepic);

        email2 = fragment_edit_userArgs.fromBundle(getArguments()).getEmail();
        name2 = fragment_edit_userArgs.fromBundle(getArguments()).getName();
        phone2 = fragment_edit_userArgs.fromBundle(getArguments()).getPhone();
        address2 = fragment_edit_userArgs.fromBundle(getArguments()).getAddress();
        isEducator2 = fragment_edit_userArgs.fromBundle(getArguments()).getIsEducator();
        isPaernt2 = fragment_edit_userArgs.fromBundle(getArguments()).getIsParent();
        password2 = fragment_edit_userArgs.fromBundle(getArguments()).getPassword();
        urlImage = fragment_edit_userArgs.fromBundle(getArguments()).getUrlImage();

        if (urlImage != null) {
            Picasso.get().load(urlImage).into(picImage);
        }

        email.setText(email2);
        name.setText(name2);
        phone.setText(phone2);
        address.setText(address2);
        isEducator.setChecked(isEducator2);
        isParent.setChecked(isPaernt2);
        password.setText(password2);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        btn_camera.setOnClickListener(v -> {
            openCam();
        });

        btn_gallery.setOnClickListener(v -> {
            openGallery();
        });

        email.setEnabled(false);

        // Inflate the layout for this fragment
        return view;
    }

    public void openCam() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void openGallery() {
        Intent photoPicerIntent = new Intent(Intent.ACTION_PICK);
        photoPicerIntent.setType("image/*");
        startActivityForResult(photoPicerIntent, REQUEST_IMAGE_PIC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                picImage.setImageBitmap(imageBitmap);
            }
        } else if (requestCode == REQUEST_IMAGE_PIC) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    picImage.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void edit() {
        progressBar.setVisibility(View.VISIBLE);

        edit.setEnabled(false);

        String address3 = address.getText().toString();
        String email3 = email.getText().toString();
        String name3 = name.getText().toString();
        String phone3 = phone.getText().toString();
        Boolean educated3 = isEducator.isChecked();
        Boolean parent3 = isParent.isChecked();
        String password3 = password.getText().toString();

        Profile profile1 = new Profile(name3, address3, email3, password3, educated3, parent3, phone3);

        if (imageBitmap != null) {
            Model.instance.saveImage(imageBitmap, email3 + ".jpg", url -> {
                profile1.setUrlImage(url);
                Model.instance.sighin(profile1, new ModelFireBase.sighup() {
                    @Override
                    public void onComplete(String email) {
                        Model.instance.mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                Navigation.findNavController(view).navigate(R.id.action_edit_user_to_fragment_profile);

                            }
                        });
                    }
                });

            });

        } else {
            profile1.setUrlImage(urlImage);
            Model.instance.sighin(profile1, new ModelFireBase.sighup() {
                @Override
                public void onComplete(String email) {
                    Model.instance.mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            Navigation.findNavController(view).navigate(R.id.action_edit_user_to_fragment_profile);
                        }
                    });
                }
            });
        }
    }
}