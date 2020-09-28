package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class woofshop_viewBill extends AppCompatActivity {

    Button confirmOrder;
    String toalPrice;
    TextView totalpriceTxt;
    EditText inputAddress, inputPostal, inputPhone, inputcusName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofshop_view_bill);

        inputAddress = findViewById(R.id.order_address);
        inputPostal = findViewById(R.id.order_postal);
        inputPhone = findViewById(R.id.phone_orders);
        inputcusName = findViewById(R.id.order_cusName);

        confirmOrder = findViewById(R.id.woofshop_confirm_order);
        totalpriceTxt = findViewById(R.id.totalPrice_order);

        toalPrice = getIntent().getStringExtra("totalPrice");
        totalpriceTxt.setText("Rs."+toalPrice);

    }


    @Override
    protected void onResume() {
        super.onResume();

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofShop);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.bottomNaviBar_woofCorner:
                        startActivity(new Intent(getApplicationContext(), woofcorner_show_ads.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_woofCare:
                        startActivity(new Intent(getApplicationContext(), woofcare_show_clinics.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_woofShop:
                        startActivity(new Intent(getApplicationContext(), woofshop_show_products.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_woofProfile:
                        startActivity(new Intent(getApplicationContext(), app_woofprofile_menu.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });
        //bottom navigation bar ends

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate postal code and address
                boolean res = validateInputOrderDetails();

                //valid
                if(res)
                {
                    saveOrderDetails();
                }

            }
        });
    }

    public boolean validateInputOrderDetails()
    {
        boolean chk = false;

        String address, postal, phone, name;

        phone = inputPhone.getText().toString();
        address = inputAddress.getText().toString();
        postal = inputPostal.getText().toString();
        name = inputcusName.getText().toString();

        if(name.isEmpty() || name.length() < 5)
        {
            Toast.makeText(getApplicationContext(), "Please enter valid Name", Toast.LENGTH_SHORT).show();
        }

        else if(address.isEmpty() || address.length() < 10)
        {
            Toast.makeText(getApplicationContext(), "Please enter valid address", Toast.LENGTH_LONG).show();
        }
        else if(phone.length() != 10 || phone.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
        }
        else if(postal.length() != 5 || postal.isEmpty() )
        {
            Toast.makeText(getApplicationContext(), "Please enter valid postal code", Toast.LENGTH_LONG).show();
        }
        else
        {
            chk = true;
        }

        return chk;
    }

    public void saveOrderDetails()
    {
        String date, time;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy MMM dd");
        date = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        time = currentTime.format(calendar.getTime());

        String orderdDate = date + " " + time;

        //get user in auth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = user.getUid();

        final DatabaseReference ordRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(userID);

        Order order = new Order();
        order.setAddress(inputAddress.getText().toString());
        order.setPhone(inputPostal.getText().toString());
        order.setPostalCode(inputPhone.getText().toString());
        order.setTotalAmount(Float.parseFloat(toalPrice));
        order.setName(inputcusName.getText().toString());
        order.setStatus("Pending");
        order.setDateOrdered(orderdDate);

        //add date to product
        ordRef.setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    //remove items from cart
                    FirebaseDatabase.getInstance().getReference().child("CartList").child("User").child(userID).removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Order added Successfully", Toast.LENGTH_SHORT);
                                Intent intent = new Intent(woofshop_viewBill.this, woofshop_show_products.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error:"+task.getException().toString(), Toast.LENGTH_SHORT);
                            }
                        }
                    });

                }

            }
        });

    }

}