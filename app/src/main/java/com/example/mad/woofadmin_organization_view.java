package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class woofadmin_organization_view extends AppCompatActivity {

    Button buttonselect, buttonadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_organization_view);

        buttonselect = findViewById(R.id.woofadmin_org_selectbutton);
        buttonadd = findViewById(R.id.woofadmin_org_addbutton);

        buttonselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_organization_view.this,woofadmin_org_details.class);
                startActivity(intent);
            }
        });

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_organization_view.this,woofadmin_org_addDetails.class);
                startActivity(intent);
            }
        });
    }
}