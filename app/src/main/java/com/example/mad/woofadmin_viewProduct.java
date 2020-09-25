package com.example.mad;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class woofadmin_viewProduct extends AppCompatActivity {

    FirebaseStorage mStorage;
    FirebaseDatabase mDatabase;
    DatabaseReference dbRef;
    RecyclerView recyclerView;
    ProductItem_Adapter productItem_adapter;
    List<ProductItem>productItemList;

    TextView toViewProduct;
    Button addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_view_product);

        addItem = findViewById(R.id.btnAdmin_addItem);

//        mDatabase = FirebaseDatabase.getInstance();
//        dbRef = FirebaseDatabase.getInstance().getReference().child("ProductItem");
//        mStorage = FirebaseStorage.getInstance();
//        recyclerView = findViewById(R.id.recyclerView_Products);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        productItemList=new ArrayList<ProductItem>();
//        productItem_adapter =new ProductItem_Adapter(woofadmin_viewProduct.this,productItemList);
//        recyclerView.setAdapter(productItem_adapter);

//        dbRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                ProductItem productItem=snapshot.getValue(ProductItem.class);
//                productItemList.add(productItem);
//                productItem_adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        toViewProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(woofadmin_viewProduct.this,woofadmin_selectedProductView.class);
//                startActivity(intent);
//            }
//        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_viewProduct.this,woofadmin_addItem.class);
                startActivity(intent);
            }
        });

    }
}