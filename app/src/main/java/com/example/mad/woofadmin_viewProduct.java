package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class woofadmin_viewProduct extends AppCompatActivity {


    TextView toViewProduct;
    Button addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_view_product);

        toViewProduct = findViewById(R.id.btnviewitem);
        addItem = findViewById(R.id.btnAdmin_addItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_viewProduct.this,woofadmin_selectedProductView.class);
                startActivity(intent);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_viewProduct.this,woofadmin_addItem.class);
                startActivity(intent);
            }
        });

    }
}