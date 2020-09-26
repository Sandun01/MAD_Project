package com.example.mad;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class woofadmin_selectedProductView extends AppCompatActivity {

    String itemID;
    TextView name,price,desc,qty;
    DatabaseReference dbRef;
    Button delete,update;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_selected_product_view);

        itemID =  getIntent().getStringExtra("itmID");

        name = findViewById(R.id.EtinputProductname);
        price = findViewById(R.id.EtinputUnitPrice);
        desc = findViewById(R.id.EtinputDescription);
        qty= findViewById(R.id.EtinputQty);
        image = findViewById(R.id.productImage);

        delete=findViewById(R.id.btnProdViewDel);
        update=findViewById(R.id.btnProdViewUpdate);


        dbRef =FirebaseDatabase.getInstance().getReference().child("ProductItem").child(itemID);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    ProductItem item = snapshot.getValue(ProductItem.class);

                    String itmName = item.getProductName();
                    float itmPrice = item.getUnitPrice();
                    int itmQty = item.getQty();
                    String itemDes = item.getDescription();

                    name.setText(itmName);
                    price.setText(String.valueOf(itmPrice));
                    desc.setText(itemDes);
                    qty.setText(String.valueOf(itmQty));
                    Picasso.get().load(item.getImage()).into(image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}