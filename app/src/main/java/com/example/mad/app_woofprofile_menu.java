package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class app_woofprofile_menu extends AppCompatActivity {

    Button orders,profile,myAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_woofprofile_menu);

        orders = findViewById(R.id.woofprofile_menu_ordersbtn);
        myAd = findViewById(R.id.woofprofile_menu_adsbtn);
        profile =findViewById(R.id.woofprofile_menu_profilebtn);

    }

    @Override
    protected void onResume() {
        super.onResume();

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
        //bottom navigation bar ends

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(app_woofprofile_menu.this, woofprofile_viewProfile.class);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(app_woofprofile_menu.this, woofshop_show_orders.class);
                startActivity(intent);
            }
        });

        myAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(app_woofprofile_menu.this, woofcorner_myAds.class);
                startActivity(intent);
            }
        });
    }

}