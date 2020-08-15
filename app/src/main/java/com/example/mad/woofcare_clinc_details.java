package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class woofcare_clinc_details extends AppCompatActivity {

    ImageView appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcare_clinc_details);

        //bottom navigation bar begins
                BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
                //set selected
                bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofCare);

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
                                return true;

                            case R.id.bottomNaviBar_woofShop:
                                startActivity(new Intent(getApplicationContext(), woofshop_show_products.class));
                                overridePendingTransition(0,0);
                                return true;

                            case R.id.bottomNaviBar_woofProfile:
                                startActivity(new Intent(getApplicationContext(), app_woofprofile_menu.class));
                                overridePendingTransition(0,0);
                                return true;

                        }

                        return false;
                    }
                });
                //bottom navigation bar ends


    }



}