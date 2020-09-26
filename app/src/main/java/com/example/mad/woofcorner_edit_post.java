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

public class woofcorner_edit_post extends AppCompatActivity {

    EditText type,price,description,contactNo,email;
    Button save,cancel;
    ImageView imageView;
    DatabaseReference dbRef;
    StorageReference storageReference;

    Dog dog;

    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    private  void clearControls(){
        type.setText("");
        price.setText("");
        description.setText("");
        contactNo.setText("");
        email.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcorner_edit_post);

        type = (EditText)findViewById(R.id.editTextTextPersonName6);
        price = (EditText)findViewById(R.id.editTextTextPersonName5);
        description = (EditText)findViewById(R.id.editTextTextPersonName4);
        contactNo = (EditText)findViewById(R.id.editTextPhone2);
        email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        imageView = (ImageView)findViewById(R.id.view_post_image);

        storageReference = FirebaseStorage.getInstance().getReference("images");

        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Dog");
        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("")){
//                    dog.setype(type.getText().toString().trim());
//                    dog.setDogType(price.getText().toString().trim());
//                    dog.setDogType(description.getText().toString().trim());
//                    dog.setDogType(contactNo.getText().toString().trim());
//                    dog.setDogType(email.getText().toString().trim());

                    dbRef = FirebaseDatabase.getInstance().getReference().child("Dog").child("");
                    dbRef.setValue(dog);
                    clearControls();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      imageView.setOnClickListener(new View.OnClickListener() {
           @Override public void onClick(View view) {
              openImage();
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
        save = findViewById(R.id.button3);
        cancel = findViewById(R.id.edit_post_cancel);
    }

          private void openImage() {
            Intent intent = new Intent();
            intent.setType("image/*");
           intent.setAction(Intent.ACTION_GET_CONTENT);
           startActivityForResult(intent,IMAGE_REQUEST);
       }

//    private String getFileExtension(Uri uri){
//        ContentResolver contentResolver = getContext().getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//
//    }

//    private void uploadImage(){
//        final ProgressDialog pd = new ProgressDialog(getContext());
//        pd.setMessage("Uploading");
//        pd.show();
//
//        if (imageUri != null){
//            final StorageReference fileReference = storageReference.child(System.currentTimeMillis());
//
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }
}