package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.DogCare;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class woofadmin_org_update extends AppCompatActivity {

    EditText txtClinic, txtConNo, txtAddress, txtCity, txtDescription, txtOwner;
    Button btnUpdate;
    DatabaseReference upDbRef;
    DogCare updateClinic;
    private String org_id = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_org_update);

        org_id = getIntent().getStringExtra("id");

        txtClinic = findViewById(R.id.updateClinic);
        txtConNo = findViewById(R.id.updateConNo);
        txtAddress = findViewById(R.id.updateAddress);
        txtCity = findViewById(R.id.updateCity);
        txtDescription = findViewById(R.id.updateDescription);
        txtOwner = findViewById(R.id.updateOwner);
        btnUpdate = findViewById(R.id.woofadmin_org_update);

        updateClinic = new DogCare();
        getOrgDetails(org_id);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("DogCare");
                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(org_id)){
                            try{
                                updateClinic.setClinicName(txtClinic.getText().toString());
                                updateClinic.setContactNo(txtConNo.getText().toString());
                                updateClinic.setAddress(txtAddress.getText().toString());
                                updateClinic.setCity(txtCity.getText().toString());
                                updateClinic.setDescription(txtDescription.getText().toString());
                                updateClinic.setOwnerName(txtOwner.getText().toString());

                                upDbRef = FirebaseDatabase.getInstance().getReference().child("DogCare").child(org_id);
                                upDbRef.setValue(org_id);
                                //Feedback to the user via a Toast...
                                Toast.makeText(getApplicationContext(), "Data Updated Successfully...", Toast.LENGTH_SHORT).show();
                            }
                            catch(NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number...", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source to Update...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


    private void getOrgDetails(String org_id) {
        DatabaseReference orgRef = FirebaseDatabase.getInstance().getReference().child("DogCare");

        orgRef.child(org_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    DogCare clinics = snapshot.getValue(DogCare.class);

                    txtClinic.setText(clinics.getClinicName());
                    txtAddress.setText(clinics.getAddress());
                    txtConNo.setText(clinics.getContactNo());
                    txtCity.setText(clinics.getCity());
                    txtDescription.setText(clinics.getDescription());
                    txtOwner.setText(clinics.getOwnerName());

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
        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_admin_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_adminProfile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.bottomNaviBar_adminOrganizations:
                        startActivity(new Intent(getApplicationContext(), woofadmin_organization_view.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminItems:
                        startActivity(new Intent(getApplicationContext(), woofadmin_addItem.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminOrders:
                        startActivity(new Intent(getApplicationContext(), woofadmin_orders.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminProfile:
                        return true;

                }

                return false;
            }
        });
    }
}