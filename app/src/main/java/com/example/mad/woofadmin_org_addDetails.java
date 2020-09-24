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

public class woofadmin_org_addDetails extends AppCompatActivity {

    EditText txtClinickName, txtContactNo, txtAddress, txtCity, txtDescription, txtOwner;
    Button btnAdd;
    DatabaseReference dbRef;
    DogCare clinic;

    private void clearControls(){
        txtClinickName.setText("");
        txtContactNo.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtDescription.setText("");
        txtOwner.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_org_add_details);

        txtClinickName = findViewById(R.id.cName);
        txtContactNo = findViewById(R.id.ConNo);
        txtAddress = findViewById(R.id.Cadd);
        txtCity = findViewById(R.id.Ccity);
        txtDescription = findViewById(R.id.cDes);
        txtOwner = findViewById(R.id.cOwner);
        btnAdd = findViewById(R.id.woofadmin_org_update);

        clinic = new DogCare();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("DogCare");
                try{
                    if (TextUtils.isEmpty(txtClinickName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Clinic Name...", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContactNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Contact Number...", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtAddress.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter an Address...", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtCity.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a City...", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtDescription.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Description", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtOwner.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter an Owner Name...", Toast.LENGTH_SHORT).show();
                    else{
                        //Take inputs from the user and assigning them to this instance(clinic) of the DogCare...
                        clinic.setClinicName(txtClinickName.getText().toString().trim());
                        clinic.setContactNo(txtContactNo.getText().toString().trim());
                        clinic.setAddress(txtAddress.getText().toString().trim());
                        clinic.setCity(txtCity.getText().toString().trim());
                        clinic.setDescription(txtDescription.getText().toString().trim());
                        clinic.setOwnerName(txtOwner.getText().toString().trim());
                        //Insert into the database
                        dbRef.push().setValue(clinic);
                        //Feedback to the user via a Toast...
                        Toast.makeText(getApplicationContext(), "Data Saved Successfully...", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Invalid Contact Number...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}