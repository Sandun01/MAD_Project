package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class woofadmin_menu extends AppCompatActivity {

    Button carebtn,itemMng,toAdminpro,ordersbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_menu);

        carebtn = findViewById(R.id.woofprofile_menu_ordersbtn);
        itemMng = findViewById(R.id.woofprofile_menu_items);
        toAdminpro = findViewById(R.id.woofprofile_menu_adsbtn);
        ordersbtn = findViewById(R.id.woofprofile_menu_profilebtn);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_menu.this, woofadmin_orders.class);
                startActivity(intent);
            }
        });

        carebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_menu.this, woofadmin_organization_view.class);
                startActivity(intent);
            }
        });

        itemMng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_menu.this, woofadmin_viewProduct.class);
                startActivity(intent);
            }
        });


        toAdminpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_menu.this, admin_account.class);
                startActivity(intent);
            }
        });

    }
}
