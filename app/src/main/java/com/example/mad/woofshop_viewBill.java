package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class woofshop_viewBill extends AppCompatActivity {

    Button view_dil_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofshop_view_bill);

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofShop);

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

        view_dil_form = findViewById(R.id.woofshop_confirm_order);
    }


    @Override
    protected void onResume() {
        super.onResume();

        view_dil_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}