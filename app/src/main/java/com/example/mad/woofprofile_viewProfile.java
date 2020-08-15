package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class woofprofile_viewProfile extends AppCompatActivity {


    Button toUpdateOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofprofile_view_profile);

        toUpdateOwner = findViewById(R.id.owner_update);

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofProfile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.bottomNaviBar_woofCorner:
                        startActivity(new Intent(getApplicationContext(), woofcorner_show_ads.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_woofCare:
                        startActivity(new Intent(getApplicationContext(), woofcare_show_clinics.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_woofShop:
                        startActivity(new Intent(getApplicationContext(), woofshop_show_products.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_woofProfile:
                        return true;

                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        toUpdateOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofprofile_viewProfile.this,woofprofile_updateProfile.class);
                startActivity(intent);
            }
        });
    }
}