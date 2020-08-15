package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class woofcare_show_clinics extends AppCompatActivity {

    Button viewNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcare_show_clinics);

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

        //declaring variables
        viewNext = findViewById(R.id.woofadmin_org_selectbutton);

    }

    @Override
    protected void onResume() {
        super.onResume();

        viewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToviewDetails();
            }
        });
    }

    public void navigateToviewDetails()
    {
        Intent intent = new Intent(woofcare_show_clinics.this, woofcare_clinc_details.class);
        startActivity(intent);
    }

}