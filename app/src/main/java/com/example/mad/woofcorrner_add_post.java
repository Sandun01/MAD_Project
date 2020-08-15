package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class woofcorrner_add_post extends AppCompatActivity {

    Button post,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcorrner_add_post);

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofCorner);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bottomNaviBar_woofCorner:
                        return true;

                    case R.id.bottomNaviBar_woofCare:
                        startActivity(new Intent(getApplicationContext(), woofcare_show_clinics.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottomNaviBar_woofShop:
                        startActivity(new Intent(getApplicationContext(), woofshop_show_products.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottomNaviBar_woofProfile:
                        startActivity(new Intent(getApplicationContext(), app_woofprofile_menu.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });
        post = findViewById(R.id.addpost);
        cancel = findViewById(R.id.edit_post_cancel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofcorrner_add_post.this, woofcorner_myAds.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofcorrner_add_post.this, woofcorner_myAds.class);
               startActivity(intent);
            }
        });
    }
}