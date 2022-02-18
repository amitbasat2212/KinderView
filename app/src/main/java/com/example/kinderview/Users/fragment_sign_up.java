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
import com.example.kinderview.feed.MainActivity;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Profile;
import com.example.kinderview.viewModel.SignupViewModel;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class fragment_sign_up extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PIC = 2;
    EditText password, email1, name, phone, address;
    CheckBox eductor,parent;
    Button btnlogin, btncanel;
    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressBar progressBar;
    View view;
    ImageView imageprofile;
    Bitmap imageBitmap;
    ImageButton CamrabUTTON,GallertButton;
    SignupViewModel signupViewModel;
    String patterns
            = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
    Pattern pattern = Pattern.compile(patterns);
    String regexName ="[a-zA-Z]+\\.?";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        email1 = view.findViewById(R.id.signin_email_et);
        password = view.findViewById(R.id.signin_password_et);
        name = view.findViewById(R.id.signin_name);
        phone = view.findViewById(R.id.signin_phone);
        address = view.findViewById(R.id.signin_address);
        eductor=view.findViewById(R.id.signin_educator);
        parent=view.findViewById(R.id.signin_parent);
        CamrabUTTON=view.findViewById(R.id.fragment_sighup_camra);
        GallertButton=view.findViewById(R.id.fragment_sighup_gallery);
        imageprofile = view.findViewById(R.id.fragment_sighup_profile);
        btnlogin = view.findViewById(R.id.signin_login_btn);
        progressBar = view.findViewById(R.id.fragment_sighup_progressbar);
        progressBar.setVisibility(View.GONE);
        btncanel = view.findViewById(R.id.signin_cancel_btn);

        btnlogin.setOnClickListener(v -> {
            perforAuth();
        });

        btncanel.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_sign_up_to_fragment_login);

        });

        CamrabUTTON.setOnClickListener(v -> {
            openCam();
        });

        GallertButton.setOnClickListener(v -> {
            openGallery();
        });

        // Inflate the layout for this fragment
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
                imageprofile.setImageBitmap(imageBitmap);
            }
        }else if(requestCode==REQUEST_IMAGE_PIC){
            if(resultCode==RESULT_OK){
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    imageprofile.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    private void perforAuth() {
        String password1 = password.getText().toString();
        String email2 = email1.getText().toString();
        String phone1 = phone.getText().toString();
        String address1 = address.getText().toString();
        String name1 = name.getText().toString();
        boolean educator1 = eductor.isChecked();
        boolean parent1 = parent.isChecked();
        progressBar.setVisibility(View.VISIBLE);
        btnlogin.setEnabled(false);
        btncanel.setEnabled(false);
        GallertButton.setEnabled(false);
        CamrabUTTON.setEnabled(false);
        email1.setEnabled(false);
        password.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        address.setEnabled(false);
        eductor.setEnabled(false);
        parent.setEnabled(false);

        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(phone1);
        Profile profile=new Profile(name1,address1,email2,password1,educator1,parent1,phone1);

        if(!email2.matches(emailPattern))
        {
            Toast.makeText(getContext(), "Email is not correct", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btnlogin.setEnabled(true);
            btncanel.setEnabled(true);
            GallertButton.setEnabled(true);
            CamrabUTTON.setEnabled(true);
            email1.setEnabled(true);
            password.setEnabled(true);
            name.setEnabled(true);
            phone.setEnabled(true);
            address.setEnabled(true);
            eductor.setEnabled(true);
            parent.setEnabled(true);
            return;

        }

        else if(password1.isEmpty() || password1.length()<6){
            Toast.makeText(getContext(), "You can write only 6 char in password", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btnlogin.setEnabled(true);
            btncanel.setEnabled(true);
            GallertButton.setEnabled(true);
            CamrabUTTON.setEnabled(true);
            email1.setEnabled(true);
            password.setEnabled(true);
            name.setEnabled(true);
            phone.setEnabled(true);
            address.setEnabled(true);
            eductor.setEnabled(true);
            parent.setEnabled(true);
            return;
        }
        if(!name1.matches(regexName)){
            Toast.makeText(getContext(), "the name format is not correct", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            btnlogin.setEnabled(true);
            btncanel.setEnabled(true);
            GallertButton.setEnabled(true);
            CamrabUTTON.setEnabled(true);
            email1.setEnabled(true);
            password.setEnabled(true);
            name.setEnabled(true);
            phone.setEnabled(true);
            address.setEnabled(true);
            eductor.setEnabled(true);
            parent.setEnabled(true);
            return;
        }

        if(!matcher.matches()){
            Toast.makeText(getContext(), "the phone format is not correct", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            btnlogin.setEnabled(true);
            btncanel.setEnabled(true);
            GallertButton.setEnabled(true);
            CamrabUTTON.setEnabled(true);
            email1.setEnabled(true);
            password.setEnabled(true);
            name.setEnabled(true);
            phone.setEnabled(true);
            address.setEnabled(true);
            eductor.setEnabled(true);
            parent.setEnabled(true);
            return;
        }



        if(imageBitmap!=null){
            Model.instance.saveImage(imageBitmap, profile.getEmail() + ".jpg", url -> {
                profile.setUrlImage(url);
                signupViewModel.sighup(profile, new Model.sighup() {
                    @Override
                    public void onComplete(String email) {
                        if(email1!=null) {
                            toFeedActivity();
                        }else{
                            Toast.makeText(getContext(), "User not Existed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btnlogin.setEnabled(true);
                            btncanel.setEnabled(true);
                            GallertButton.setEnabled(true);
                            CamrabUTTON.setEnabled(true);
                            email1.setEnabled(true);
                            password.setEnabled(true);
                            name.setEnabled(true);
                            phone.setEnabled(true);
                            address.setEnabled(true);
                            eductor.setEnabled(true);
                            parent.setEnabled(true);
                            return;
                        }
                    }
                });
         });

        }else{
            signupViewModel.sighup(profile, new Model.sighup() {
                @Override
                public void onComplete(String email) {
                    profile.setUrlImage("0");
                    if(email!=null) {
                        toFeedActivity();
                    }else{
                            Toast.makeText(getContext(), "don't have password or email correct", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btnlogin.setEnabled(true);
                            btncanel.setEnabled(true);
                            GallertButton.setEnabled(true);
                            CamrabUTTON.setEnabled(true);
                            email1.setEnabled(true);
                            password.setEnabled(true);
                            name.setEnabled(true);
                            phone.setEnabled(true);
                            address.setEnabled(true);
                            eductor.setEnabled(true);
                            parent.setEnabled(true);
                            return;
                        }

                }
            });
        }


    }
}