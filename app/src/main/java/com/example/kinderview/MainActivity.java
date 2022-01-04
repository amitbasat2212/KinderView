package com.example.kinderview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

                    break;
                case R.id.menu_logout:

                    break;
                case R.id.menu_profile:

                    break;
            }


        }else{

        }

        return false;
    }
}