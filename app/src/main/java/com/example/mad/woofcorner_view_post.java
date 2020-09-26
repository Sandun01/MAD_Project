package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.Dog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class woofcorner_view_post extends AppCompatActivity {

    TextView type,price,description,contactNo,email;
    ImageView imageView;
    Button edit,remove;
    DatabaseReference dbRef;

    String dogID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcorner_view_post);

        type = (TextView)findViewById(R.id.viewpost_type);
        price = (TextView)findViewById(R.id.view_post_price);
        description = (TextView)findViewById(R.id.view_post_desc);
        contactNo = (TextView)findViewById(R.id.view_post_phone);
        email = (TextView)findViewById(R.id.view_post_email);
        imageView = (ImageView)findViewById(R.id.view_post_image);

        dbRef = FirebaseDatabase.getInstance().getReference();

        dogID =  getIntent().getStringExtra("did");

        getDogDetails(dogID);

        edit = findViewById(R.id.view_post_edit);
        remove = findViewById(R.id.edit_post_cancel);

    }

    private void getDogDetails(String dogID) {
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Dog");

        dataRef.child(dogID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Dog dog = snapshot.getValue(Dog.class);

                    type.setText(dog.getType());
                    price.setText(dog.getPrice().toString());
                    description.setText(dog.getDescription());
                    contactNo.setText(dog.getContactNo().toString());
                    email.setText(dog.getEmail());
                    Picasso.get().load(dog.getImage()).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofcorner_view_post.this, woofcorner_edit_post.class);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Dog");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(dogID)){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Dog").child(dogID);
                            dbRef.removeValue();
                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(woofcorner_view_post.this, woofcorner_myAds.class);
                            startActivity(intent);

                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Delete",Toast.LENGTH_SHORT).show();
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
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofCorner);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bottomNaviBar_woofCorner:
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
                        startActivity(new Intent(getApplicationContext(), app_woofprofile_menu.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });

    }


}