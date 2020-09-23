package com.example.mad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class woofcorner_view_post extends AppCompatActivity {

    TextView type,price,description,contactNo,email;
    ImageView imageView;
    Button edit,remove;
    DatabaseReference dbRef;

    private static final String TAG = "woofcorner_view_post";

    private void clearControls(){
        type.setText("");
        price.setText("");
        description.setText("");
        contactNo.setText("");
        email.setText("");
        imageView.setImageURI(Uri.parse(""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcorner_view_post);

        Log.d(TAG, "onCreate: started");

        type = (TextView)findViewById(R.id.textView9);
        price = (TextView)findViewById(R.id.textView7);
        description = (TextView)findViewById(R.id.textView11);
        contactNo = (TextView)findViewById(R.id.textView32);
        email = (TextView)findViewById(R.id.textView34);
        imageView = (ImageView)findViewById(R.id.imageView5);

        edit = findViewById(R.id.view_post_edit);
        remove = findViewById(R.id.edit_post_cancel);


        dbRef = FirebaseDatabase.getInstance().getReference();

        Query query = dbRef.child("Dog");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    type.setText(snapshot.child("dogType").getValue().toString());
                    price.setText(snapshot.child("price").getValue().toString());
                    description.setText(snapshot.child("description").getValue().toString());
                    contactNo.setText(snapshot.child("contactNo").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());

                    String url = snapshot.child("url").getValue().toString();

                    Log.d(TAG, "onDataChange : url :" +url);

                    if (!url.isEmpty()){
                        Picasso.get().load(url).into(imageView);
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"No source to Display",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                        if (snapshot.hasChild("")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Dog").child("");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully",Toast.LENGTH_SHORT).show();
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
    }


}