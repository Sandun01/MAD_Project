package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    ImageButton woofcare,woofcorner,woofshop,woofprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        woofcorner = findViewById(R.id.btn_woofcorner);
        woofprofile = findViewById(R.id.btn_woofprofile);
        woofshop =findViewById(R.id.btn_woofshop);
        woofcare = findViewById(R.id.btn_woofcare);

    }


    @Override
    protected void onResume() {
        super.onResume();

        woofshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,woofshop_show_products.class);
                startActivity(intent);

            }
        });

        woofcorner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,woofcorner_show_ads.class);
                startActivity(intent);

            }
        });


        woofcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,woofcare_show_clinics.class);
                startActivity(intent);
            }
        });


        woofprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,app_woofprofile_menu.class);
                startActivity(intent);
            }
        });

    }
}
// merge fix