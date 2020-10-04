package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.Cart;
import com.example.mad.models.ProductItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class woofshop_view_product extends AppCompatActivity {

    private String itemID, userID;
    Button addTocart;
    ImageView itemPic;
    TextView itemNameTxt, itemPriceTxt, itemDesTxt, itemQtytxt;
    String itmName, itemDes;
    int itmQty;
    float itmPrice;
    EditText inputQty;
    int stock,userEnterdQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofshop_view_product);

        //initializing id
        inputQty = findViewById(R.id.viewItem_inputqty);
        itemNameTxt = findViewById(R.id.viewItem_name);
        itemQtytxt = findViewById(R.id.viewItem_qty);
        itemPriceTxt= findViewById(R.id.viewItem_price);
        itemDesTxt = findViewById(R.id.viewItem_description);
        inputQty =findViewById(R.id.viewItem_inputqty);
        itemPic = findViewById(R.id.viewItem_iamge);
        addTocart = findViewById(R.id.button_addtocart);

        //get item id
        itemID = getIntent().getStringExtra("itmID");
        getItemDetails(itemID);

        //get user in auth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //add to cart button
        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check item in the cart
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("CartList").child("User")
                        .child(userID).child("ProductItem");

                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(itemID))
                        {
                            Toast.makeText(getApplicationContext(), "Item already in the cart", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(woofshop_view_product.this, woofshop_show_products.class);
                            startActivity(intent);

                        }
                        else
                        {
                            //add item to cart list
                            addItemTocart();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


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
                        startActivity(new Intent(getApplicationContext(), woofshop_show_products.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_woofProfile:
                        startActivity(new Intent(getApplicationContext(), woofprofile_menu.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });
        //bottom navigation bar ends


    }

    //get item details to show in view
    private void getItemDetails(String itemID) {

        DatabaseReference itmRed = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        itmRed.child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if(snapshot.exists())
                {
                    ProductItem item = snapshot.getValue(ProductItem.class);

                    itmName = item.getProductName();
                    itmPrice = item.getUnitPrice();
                    itmQty = item.getQty();
                    itemDes = item.getDescription();

                    itemNameTxt.setText(itmName);
                    itemPriceTxt.setText(String.valueOf(itmPrice));
                    itemDesTxt.setText(itemDes);
                    itemQtytxt.setText(String.valueOf(itmQty));
                    Picasso.get().load(item.getImage()).into(itemPic);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }

        });

    }

    //add item to cart
    public void addItemTocart()
    {
        String date, time;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy MMM dd");
        date = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        time = currentTime.format(calendar.getTime());

        //check the auantity is grater than the stock
        stock = itmQty;
        userEnterdQty = Integer.parseInt(inputQty.getText().toString());

        if(userEnterdQty > stock)
        {
            Toast.makeText(getApplicationContext(), "Invalid Quantity.maximum quantity exceeded", Toast.LENGTH_SHORT).show();
        }

        else{

            final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("CartList");

            final Cart cart = new Cart();
            cart.setItemID(itemID);
            cart.setDateAdded(date);
            cart.setTimeAdded(time);
            cart.setItemName(itmName);
            cart.setPrice(itmPrice);
            cart.setQuantity(userEnterdQty);

            cartRef.child("User").child(userID).child("ProductItem").child(itemID).setValue(cart)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        //update item quantity
                        updateProductItemStock();

                        Toast.makeText(getApplicationContext(), "Item Added to cart", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(woofshop_view_product.this, woofshop_show_products.class);
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error:"+task.getException().toString() , Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }


    public void updateProductItemStock()
    {
        final DatabaseReference dbUpdateQty = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        dbUpdateQty.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    int qtyNew = stock - userEnterdQty;
                    dbUpdateQty.child(itemID).child("qty").setValue(qtyNew);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}