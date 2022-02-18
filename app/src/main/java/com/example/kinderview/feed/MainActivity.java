package com.example.kinderview.feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.kinderview.R;
import com.example.kinderview.Users.LoginActivity;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;

public class MainActivity extends AppCompatActivity {

    NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("");
        NavHost navHost = (NavHost) getSupportFragmentManager().findFragmentById(R.id.Fragment_nav_host);
        controller = navHost.getNavController();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        System.out.println("ho");
        if(!super.onOptionsItemSelected(item)){

            switch (item.getItemId()){
                case R.id.fragments_home:
                  controller.navigate(R.id.action_global_home_page);

                    break;

                case R.id.menu_create:

                    // Model.instance.getUserName(email);
                    controller.navigate(R.id.action_global_createPost);

                    break;

                case R.id.menu_logout:
                    Intent intent = new Intent(this, LoginActivity.class);
                    Model.instance.sighout(new ModelFireBase.sighout() {
                        @Override
                        public void onComplete() {
                            startActivity(intent);
                            finish();
                        }
                    });
                    break;

                case R.id.menu_profile:
                    controller.navigate(R.id.action_global_fragment_profile);
                    break;
            }


        }

        return false;
    }
}