package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mad.models.Cart;
import com.example.mad.viewholders.UserOrderItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class woofshop_vieworderItems extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference dbRef;
    RecyclerView.LayoutManager layoutManager;
    String ordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofshop_vieworder_items);

        //get order ID
        ordID = getIntent().getStringExtra("ordID");

        //get user in auth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userID = user.getUid();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(ordID).child("Items");

        recyclerView = findViewById(R.id.showOrderedItemsView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>().setQuery(dbRef, Cart.class).build();

        FirebaseRecyclerAdapter<Cart, UserOrderItemViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, UserOrderItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserOrderItemViewHolder cartViewHolder, int i, @NonNull Cart cart) {

                cartViewHolder.txtOrdItmName.setText("Product Name: "+cart.getItemName());
                cartViewHolder.txtOrdItmPrice.setText("Price: Rs."+String.valueOf(cart.getPrice()));
                cartViewHolder.txtOrdItmQty.setText("Quantity: "+String.valueOf(cart.getQuantity()));

            }

            @NonNull
            @Override
            public UserOrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_orderitem_layout, parent, false);
                UserOrderItemViewHolder holder = new UserOrderItemViewHolder(view);
                return holder;
            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofProfile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bottomNaviBar_woofCorner:
                        startActivity(new Intent(getApplicationContext(), woofcorner_show_ads.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottomNaviBar_woofCare:
                        startActivity(new Intent(getApplicationContext(), woofcare_show_clinics.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottomNaviBar_woofShop:
                        startActivity(new Intent(getApplicationContext(), woofshop_show_products.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottomNaviBar_woofProfile:
                        startActivity(new Intent(getApplicationContext(), woofprofile_menu.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });
        //bottom navigation bar ends

    }

}