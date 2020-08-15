package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class woofadmin_org_details extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_org_details);

        button = findViewById(R.id.woofadmin_org_update);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_org_details.this,woofadmin_org_update.class);
                startActivity(intent);
            }
        });
    }
}