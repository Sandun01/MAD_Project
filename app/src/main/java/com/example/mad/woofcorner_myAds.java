package com.example.mad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class woofcorner_myAds extends AppCompatActivity {
    ImageButton view,edit;
    FloatingActionButton postAd;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcorner_my_ads);

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
        //bottom navigation bar ends
        view = findViewById(R.id.ad_view);
        edit = findViewById(R.id.ad_edit);
        postAd = findViewById(R.id.postAd_button);

    }

    @Override
    protected void onResume() {
        super.onResume();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofcorner_myAds.this, woofcorner_view_post.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofcorner_myAds.this, woofcorner_edit_post.class);
                startActivity(intent);
            }
        });

        postAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofcorner_myAds.this, woofcorrner_add_post.class);
                startActivity(intent);
            }
        });

    }
}