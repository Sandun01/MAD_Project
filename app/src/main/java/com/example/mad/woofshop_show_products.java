package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.models.ProductItem;
import com.example.mad.viewholders.ProductViewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class woofshop_show_products extends AppCompatActivity {

    FloatingActionButton btn_cart_prod;
    private DatabaseReference prRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofshop_show_products);

        //initializing
        prRef = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        btn_cart_prod = findViewById(R.id.btn_cart_prod);
        recyclerView = findViewById(R.id.product_itemview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ProductItem> options =
                new FirebaseRecyclerOptions.Builder<ProductItem>()
                        .setQuery(prRef, ProductItem.class)
                        .build();

        FirebaseRecyclerAdapter<ProductItem, ProductViewholder> adapter =
                new FirebaseRecyclerAdapter<ProductItem, ProductViewholder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewholder holder, int i, @NonNull final ProductItem model)
                    {
                        holder.pnameTxt.setText(model.getProductName());
                        holder.priceTxt.setText("Price: Rs." + model.getUnitPrice().toString());
                        holder.pdesTxt.setText("Description: " + model.getDescription());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(woofshop_show_products.this, woofshop_view_product.class);
                                intent.putExtra("itmID", model.getId());
                                startActivity(intent);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent,false);

                        ProductViewholder holder = new ProductViewholder(view);

                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //add to cart button



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
//        bottom navigation bar ends

        btn_cart_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofshop_show_products.this,woofshop_view_cart.class);
                startActivity(intent);
            }
        });

    }
}