package com.example.mad;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class woofadmin_addItem extends AppCompatActivity {

    EditText txtProdName,txtProdDescription, txtID,txtQty,txtUnitPrice;
    Button btnSave;
    DatabaseReference dbRef;
    ProductItem productItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_add_item);

        txtID = findViewById(R.id.EtinputID);
        txtQty = findViewById(R.id.EtinputQty);
        txtUnitPrice = findViewById(R.id.EtinputUnitPrice);
        txtProdName = findViewById(R.id.EtinputProductname);
        txtProdDescription = findViewById(R.id.EtinputDescription);

        btnSave = findViewById(R.id.btnAddProduct);

        productItem = new ProductItem();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("ProductItem");
                try{
                    if (TextUtils.isEmpty(txtID.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter ID",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtUnitPrice.getText().toString()))
                        Toast.makeText(getApplicationContext(),"enter quantity",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtProdName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Enter ptoduct name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtProdDescription.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Enter ptoduct name",Toast.LENGTH_SHORT).show();
                    else {
                        productItem.setID(Integer.parseInt(txtID.getText().toString().trim()));
                        productItem.setProductName(txtProdName.getText().toString());
                        productItem.setQty(Integer.parseInt(txtQty.getText().toString()));
                        productItem.setDescription(txtProdDescription.getText().toString());
                        productItem.setUnitPrice(Float.parseFloat(txtUnitPrice.getText().toString().trim()));

                        dbRef.push().setValue(productItem);

                        Toast.makeText(getApplicationContext(),"Product added to the shop",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Enter valid pricee",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}