package com.example.mad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.models.Dog;
import com.example.mad.viewholders.woofCornerAdViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class woofcorner_myAds extends AppCompatActivity {

    FloatingActionButton postAd;
    DatabaseReference dbRef;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Query mQuery;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcorner_my_ads);


        //get userID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        //filter ads by uer ID

        dbRef = FirebaseDatabase.getInstance().getReference().child("Dog");
        mQuery = dbRef.orderByChild("uID").equalTo(userID);


        recyclerView = findViewById(R.id.post_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        postAd = findViewById(R.id.postAd_button);

    }

    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Dog> options =
                new FirebaseRecyclerOptions.Builder<Dog>()
                        .setQuery(mQuery, Dog.class).build();

        FirebaseRecyclerAdapter<Dog, woofCornerAdViewHolder> adapter = new FirebaseRecyclerAdapter<Dog, woofCornerAdViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull woofCornerAdViewHolder woofCornerAdViewHolder, final int i, @NonNull final Dog dog) {
                woofCornerAdViewHolder.type.setText(dog.getType());
                woofCornerAdViewHolder.description.setText(dog.getDescription());
                woofCornerAdViewHolder.price.setText( "Rs."+String.valueOf(dog.getPrice()) );
                Picasso.get().load(dog.getImage()).into(woofCornerAdViewHolder.imageView);

                woofCornerAdViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(woofcorner_myAds.this,woofcorner_view_post.class);
                        intent.putExtra( "did", getRef(i).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public woofCornerAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.woofcorner_ads_layout  ,parent, false);
                woofCornerAdViewHolder woofCornerAdViewHolder = new woofCornerAdViewHolder(view);
                return woofCornerAdViewHolder;
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
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofCorner);

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


        postAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofcorner_myAds.this, woofcorrner_add_post.class);
                startActivity(intent);
            }
        });


    }




}