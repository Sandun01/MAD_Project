package com.example.mad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class woofcorner_edit_post extends AppCompatActivity {

    EditText type,price,description,contactNo,email;
    Button save,cancel;
    ImageView imageView;
    DatabaseReference dbRef;
    StorageReference storageReference;

    String dogID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcorner_edit_post);

        type = (EditText)findViewById(R.id.phone);
        price = (EditText)findViewById(R.id.editTextTextPersonName5);
        description = (EditText)findViewById(R.id.uname);
        contactNo = (EditText)findViewById(R.id.editTextPhone2);
        email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        imageView = (ImageView)findViewById(R.id.view_post_image);

        dbRef = FirebaseDatabase.getInstance().getReference();

        dogID =  getIntent().getStringExtra("did");

        getDogDetails(dogID);

        save = findViewById(R.id.btnProdViewDel);
        cancel = findViewById(R.id.edit_post_cancel);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference();
                dbRef.child("Dog").child(dogID).child("type").setValue(type.getText().toString().trim());
                dbRef.child("Dog").child(dogID).child("price").setValue(Double.parseDouble(price.getText().toString()));
                dbRef.child("Dog").child(dogID).child("description").setValue(description.getText().toString());
                dbRef.child("Dog").child(dogID).child("contactNo").setValue(Integer.parseInt(contactNo.getText().toString()));
                dbRef.child("Dog").child(dogID).child("email").setValue(email.getText().toString().trim());
                Intent intent = new Intent(woofcorner_edit_post.this, woofcorner_view_post.class);
                Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

       cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofcorner_edit_post.this, woofcorner_myAds.class);
                startActivity(intent);
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
                        startActivity(new Intent(getApplicationContext(), app_woofprofile_menu.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });
    }
}