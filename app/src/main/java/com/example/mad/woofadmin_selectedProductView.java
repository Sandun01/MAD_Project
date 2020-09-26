package com.example.mad;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class woofadmin_selectedProductView extends AppCompatActivity {

    String itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_selected_product_view);

        itemID =  getIntent().getStringExtra("itmID");
        System.out.println("----------------------------"+itemID);

    }
}