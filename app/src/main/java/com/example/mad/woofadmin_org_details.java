package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class woofadmin_org_details extends AppCompatActivity {

    EditText txtDelClinic, txtDelConNo, txtDelAddress, txtDelCity, txtDelDescription, txtDelOwner;
    Button buttonDelete;
    DatabaseReference dbRef;
    DogCare deleteClinic;

    private void clearControls(){
        txtDelClinic.setText("");
        txtDelConNo.setText("");
        txtDelAddress.setText("");
        txtDelCity.setText("");
        txtDelDescription.setText("");
        txtDelOwner.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_org_details);

        txtDelClinic = findViewById(R.id.deleteClinic);
        txtDelConNo = findViewById(R.id.deleteConNo);
        txtDelAddress = findViewById(R.id.deleteAddress);
        txtDelCity = findViewById(R.id.deleteCity);
        txtDelDescription = findViewById(R.id.deleteDescription);
        txtDelOwner = findViewById(R.id.deleteOwner);

        buttonDelete = findViewById(R.id.woofadmin_org_update);

        deleteClinic = new DogCare();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("deleteClinic");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("deleteClinic1")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("DogCare").child("deleteClinic1");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(), "Data Deleted Successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No source to Delete...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_org_details.this,woofadmin_org_update.class);
                startActivity(intent);
            }
        });
    }
}