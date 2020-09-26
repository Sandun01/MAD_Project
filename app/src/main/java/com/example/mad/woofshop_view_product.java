package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.ProductItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class woofshop_view_product extends AppCompatActivity {

    String itemID;

    ImageView opencart, itemPic;
    TextView itemNameTxt, itemPriceTxt, itemDesTxt, itemQtytxt;
    EditText inputQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofshop_view_product);

        //initializing id
        opencart = findViewById(R.id.btn_cart_prod);
        inputQty = findViewById(R.id.viewItem_inputqty);
        itemNameTxt = findViewById(R.id.viewItem_name);
        itemQtytxt = findViewById(R.id.viewItem_qty);
        itemPriceTxt= findViewById(R.id.viewItem_price);
        itemDesTxt = findViewById(R.id.viewItem_description);
        inputQty =findViewById(R.id.viewItem_inputqty);
        itemPic = findViewById(R.id.viewItem_iamge);

        //get item id
        itemID = getIntent().getStringExtra("itmID");
        getItemDetails(itemID);

    }

    @Override
    protected void onResume() {
        super.onResume();

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

        opencart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofshop_view_product.this,woofshop_view_cart.class);
                startActivity(intent);
            }
        });
    }

    private void getItemDetails(final String itemID) {

        DatabaseReference itmRed = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        itmRed.child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if(snapshot.exists())
                {
                    ProductItem item = snapshot.getValue(ProductItem.class);

                    itemNameTxt.setText(item.getProductName());
                    itemPriceTxt.setText(item.getUnitPrice().toString());
                    itemDesTxt.setText(item.getDescription());
                    itemQtytxt.setText(item.getQty().toString());
                    Picasso.get().load(item.getImage()).into(itemPic);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }

        });


    }

}