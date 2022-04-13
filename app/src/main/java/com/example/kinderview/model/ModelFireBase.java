package com.example.kinderview.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFireBase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseUser currentUser;
    /**
     * the Constructor section
     */
    public ModelFireBase(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    /**
     * the interfaces section of the profile and posts
     */
    public interface GetAllPostsListener{
        void onComplete(List<Post> list);
    }

    public interface sighout{
        void onComplete();
    }


    /**
     * the posts section-add,edit and delete
     */
    public void getAllPosts(Long lastUpdateDate, GetAllPostsListener listener) {
        db.collection(Post.COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("updateDate",new Timestamp(lastUpdateDate,0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Post> list = new LinkedList<Post>();
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot doc : task.getResult()){
                            Post post = Post.create(doc.getData());
                            if (post != null){
                                list.add(post);
                            }
                        }
                    }
                    listener.onComplete(list);
                });
    }

    public void addPost(Post post, Model.AddEditDeleteProfileAndPost listener) {
        Map<String, Object> json = post.tojson();
        db.collection(post.COLLECTION_NAME)
                .document(post.getId())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public void deletePost(Post post, Model.AddEditDeleteProfileAndPost listener) {
        post.setDelete(true);
        Map<String, Object> json = post.tojson();
        db.collection(post.COLLECTION_NAME)
                .document(post.getId())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }



    /**
     * the Storage Images for post and profile pic section
     */
    public void saveImagePost(Bitmap imagebitmap, String imagename, Model.SaveImagelistener listener) {
        StorageReference imgRef = storageRef.child("photos/" + imagename);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagebitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUrl = uri;
                            listener.onComplete(downloadUrl.toString());
                        });
                    }
                });
    }

    public void deleteImagePost(String urlImagePost, String picName, Model.AddEditDeleteProfileAndPost listener) {
        StorageReference desertRef = storageRef.child("photos/" + picName);
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete();
            }
        });

    }

    public void deleteProfilePic(String url, String picName, Model.AddEditDeleteProfileAndPost listener) {
        StorageReference desertRef = storageRef.child("photos/"+picName);

        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete();
            }
        });

    }

    /**
     * the Authentication user-login,sigh up ,sighout and the profile part -edit and create profile and find profile by id section
     */
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public Boolean isSignIn () {
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        return (currentUser != null);
    }
    public void connected () {
        currentUser = mAuth.getCurrentUser();

    }


    public void Login (String email, String password, Model.sighup listener){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        listener.onComplete(user.getEmail());
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                        updateUI(null);
                        listener.onComplete(null);

                    }
                });


    }

    public void sighout(sighout listner){
        FirebaseAuth.getInstance().signOut();
        listner.onComplete();
    }

    public void signUp(String email, String password,Model.sighup listener){
        if(!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                listener.onComplete(user.getEmail());

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                listener.onComplete(null);
                                // updateUI(null);
                            }
                        }
                    });
        }else{
            listener.onComplete(null);
        }
    }

    public void updateUI(FirebaseUser account) {
        if(account != null){
            Log.d("TAG", "not right");

        }else {
            Log.d("TAG", "right");
        }
    }

    public void addProfile(Profile profile, Model.AddEditDeleteProfileAndPost listener) {
        Map<String, Object> json = profile.tojson();
        db.collection(profile.COLLECTION_NAME2)
                .document(profile.getEmail())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void getProfileByemail(String ProfileEmail, Model.GetProfileById listener) {
        db.collection(Profile.COLLECTION_NAME2)
                .document(ProfileEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Profile profile = null;
                        if (task.isSuccessful() & task.getResult()!= null){
                            profile = Profile.create(task.getResult().getData());
                        }
                        listener.onComplete(profile);
                    }
                });

    }

}