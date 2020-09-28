package com.example.mad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class woofadmin_addItem extends AppCompatActivity {

    String itemName,itemDescription;
    float itemPrice;
    int itemQuantity;
    private String  downloadImageUrl;

    EditText txtProdName,txtProdDescription, txtID,txtQty,txtUnitPrice;
    Button btnSave;
    DatabaseReference itemDBRef;
    ProductItem productItem;
    ImageView imageProductInput;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_add_item);

        txtQty = findViewById(R.id.EtinputQty);
        txtUnitPrice = findViewById(R.id.EtinputUnitPrice);
        txtProdName = findViewById(R.id.EtinputProductname);
        txtProdDescription = findViewById(R.id.EtinputDescription);
        imageProductInput = findViewById(R.id.produt_image);

        btnSave = findViewById(R.id.btnProdViewUpdate);

        itemDBRef = FirebaseDatabase.getInstance().getReference().child("ProductItem");
        ImageRef = FirebaseStorage.getInstance().getReference().child("ProdutImages");

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


        imageProductInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenPhoneGallery();

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate item details
                try{

                    //get details
                    itemName = txtProdName.getText().toString();
                    itemDescription = txtProdDescription.getText().toString();
                    itemPrice = Float.parseFloat(txtUnitPrice.getText().toString());
                    itemQuantity = Integer.parseInt(txtQty.getText().toString());

                    if (ImageUri == null)
                        Toast.makeText(getApplicationContext(),"please Enter a product image",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtUnitPrice.getText().toString()))
                        Toast.makeText(getApplicationContext(),"enter quantity",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtProdName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Enter ptoduct name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtProdDescription.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Enter ptoduct name",Toast.LENGTH_SHORT).show();
                    else {

                        //store information of image
                        storeAllInfo();

                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Enter valid pricee or quantity",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void OpenPhoneGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode==RESULT_OK  && data!=null)
        {
            ImageUri = data.getData();
            imageProductInput.setImageURI(ImageUri);
        }

    }

    private void storeAllInfo() {


        final StorageReference imageFilePath = ImageRef.child(ImageUri.getLastPathSegment()  + ".jpg");

        final UploadTask uploadTask = imageFilePath.putFile(ImageUri);

        //show image upload error
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getApplicationContext(), "Error: "+message,Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(),"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful())
                        {
                            throw  task.getException();
                        }

                        downloadImageUrl = imageFilePath.getDownloadUrl().toString();
                        return imageFilePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(getApplicationContext(),"get image url successfully",Toast.LENGTH_SHORT).show();

                            saveProductDetailsToDatabase();

                        }

                    }
                });

            }
        });

    }

    private void saveProductDetailsToDatabase() {

        ProductItem item = new ProductItem();
        item.setDescription(itemDescription);
        item.setImage(downloadImageUrl);
        item.setQty(itemQuantity);
        item.setUnitPrice(itemPrice);
        item.setProductName(itemName);

        itemDBRef.push().setValue(item)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Product added Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(woofadmin_addItem.this, woofadmin_viewProduct.class);
                            startActivity(intent);
                        }
                        else
                        {
                            String msg = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error:"+msg,Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        //bottom navigation bar begins
//        BottomNavigationView bottomNavigationView = findViewById(R.id.app_admin_bottom_navigationbar);
//        //set selected
//        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_adminProfile);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch(item.getItemId())
//                {
//                    case R.id.bottomNaviBar_adminOrganizations:
//                        startActivity(new Intent(getApplicationContext(), woofadmin_organization_view.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.bottomNaviBar_adminItems:
//                        startActivity(new Intent(getApplicationContext(), woofadmin_addItem.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.bottomNaviBar_adminOrders:
//                        startActivity(new Intent(getApplicationContext(), woofadmin_orders.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.bottomNaviBar_adminProfile:
//                        return true;
//
//                }
//
//                return false;
//            }
//        });

    }



}