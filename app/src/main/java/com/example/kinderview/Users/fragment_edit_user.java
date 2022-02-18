package com.example.kinderview.Users;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.kinderview.R;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Profile;
import com.example.kinderview.viewModel.SignupViewModel;
import com.squareup.picasso.Picasso;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fragment_edit_user extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PIC = 2;
    String patterns
            = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
    Pattern pattern = Pattern.compile(patterns);
    String regexName ="[a-zA-Z]+\\.?";
    EditText email, password, name, phone, address;
    CheckBox isEducator, isParent;
    Button edit, cancel, deletepic;
    ProgressBar progressBar;
    String email2, password2, name2, phone2, address2, urlImage;
    Boolean isEducator2, isPaernt2;
    View view;
    ImageButton btn_gallery, btn_camera;
    ImageView picImage;
    Bitmap imageBitmap;
    SignupViewModel signupViewModel;
    int defaultImage = R.drawable.profile;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
    }

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
        cancel = view.findViewById(R.id.edituser_cancel);
        deletepic = view.findViewById(R.id.edituser_deletepic);
        email2 = fragment_edit_userArgs.fromBundle(getArguments()).getEmail();
        name2 = fragment_edit_userArgs.fromBundle(getArguments()).getName();
        phone2 = fragment_edit_userArgs.fromBundle(getArguments()).getPhone();
        address2 = fragment_edit_userArgs.fromBundle(getArguments()).getAddress();
        isEducator2 = fragment_edit_userArgs.fromBundle(getArguments()).getIsEducator();
        isPaernt2 = fragment_edit_userArgs.fromBundle(getArguments()).getIsParent();
        password2 = fragment_edit_userArgs.fromBundle(getArguments()).getPassword();
        urlImage = fragment_edit_userArgs.fromBundle(getArguments()).getUrlImage();


        if(urlImage.equals("0")){
            Picasso.get().load(R.drawable.profile).into(picImage);
        }else {
            Picasso.get().load(urlImage).into(picImage);
        }


        email.setText(email2);
        name.setText(name2);
        phone.setText(phone2);
        address.setText(address2);
        isEducator.setChecked(isEducator2);
        isParent.setChecked(isPaernt2);
        password.setText(password2);

        cancel.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_edit_user_to_fragment_profile);
        });

        deletepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        password.setEnabled(false);
        email.setEnabled(false);

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
        cancel.setEnabled(false);
        deletepic.setEnabled(false);
        String address3 = address.getText().toString();
        String email3 = email.getText().toString();
        String name3 = name.getText().toString();
        String phone3 = phone.getText().toString();
        Boolean educated3 = isEducator.isChecked();
        Boolean parent3 = isParent.isChecked();
        String password3 = password.getText().toString();
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(phone3);
        if(!matcher.matches()){
            Toast.makeText(getContext(), "the phone format is not correct", Toast.LENGTH_LONG).show();
             progressBar.setVisibility(View.GONE);
             edit.setEnabled(true);
             cancel.setEnabled(true);
             deletepic.setEnabled(true);
            return;
       }

       if(!name3.matches(regexName)){
           Toast.makeText(getContext(), "the name format is not correct", Toast.LENGTH_LONG).show();
           progressBar.setVisibility(View.GONE);
           edit.setEnabled(true);
           cancel.setEnabled(true);
           deletepic.setEnabled(true);
           return;
       }
        Profile profile1 = new Profile(name3, address3, email3, password3, educated3, parent3, phone3);

        if (imageBitmap != null) {
            Model.instance.saveImage(imageBitmap, email3 + ".jpg", url -> {
                profile1.setUrlImage(url);
                signupViewModel.editProfile(profile1, new Model.AddEditDeleteProfileAndPost() {
                    @Override
                    public void onComplete() {
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
            signupViewModel.editProfile(profile1, new Model.AddEditDeleteProfileAndPost() {
                @Override
                public void onComplete() {
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

    private void delete() {
        progressBar.setVisibility(View.VISIBLE);
        cancel.setEnabled(false);
        edit.setEnabled(false);
        deletepic.setEnabled(false);
        Profile profile2 = new Profile(name2, address2, email2, password2, isEducator2, isPaernt2, phone2);
        profile2.setUrlImage(urlImage);
        if(profile2.getUrlImage()!=null) {
            Picasso.get().load(R.drawable.profile).into(picImage);
            Model.instance.deleteProfilePic(profile2, email2 + ".jpg", () ->{
                profile2.setUrlImage("0");
                Navigation.findNavController(view).navigate(fragment_edit_userDirections.actionGlobalEditUser(profile2.getName(), profile2.getParent(), profile2.getEducator(), profile2.getPhone(),
                        profile2.getAddress(), profile2.getEmail(), profile2.getPassword(), profile2.getUrlImage())); } );
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            cancel.setEnabled(true);
            edit.setEnabled(true);
            deletepic.setEnabled(true);
            Toast.makeText(getContext(), "No picture exist", Toast.LENGTH_LONG).show();
            return;
        }

    }


}