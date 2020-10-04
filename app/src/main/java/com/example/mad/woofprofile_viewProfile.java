package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class woofprofile_viewProfile extends AppCompatActivity {

    Button toUpdateOwner, logoutBtn;
    FirebaseAuth mAuth;
    TextView uname, email, phone, verfyEmail;
    String user_id;
    boolean emailVerified;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofprofile_view_profile);

        //initialize id's
        toUpdateOwner = findViewById(R.id.viewEditOwner);
        logoutBtn = findViewById(R.id.woofAdminAddITem);

        uname = findViewById(R.id.viewUsername);
        email = findViewById(R.id.viewEmail);
        phone = findViewById(R.id.viewPhone);
        verfyEmail = findViewById(R.id.verifyuserEmail);

        //get user in auth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        emailVerified = user.isEmailVerified();

        //verified email
        if(emailVerified)
        {
            verfyEmail.setText("Your email is verified");
        }

        if(user != null)
        {
            user_id = user.getUid();
        }


        //get user details
        DatabaseReference refDB = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        refDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren())
                {
                    uname.setText(snapshot.child("username").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //get firebase instance
        mAuth = FirebaseAuth.getInstance();

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofProfile);

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
                        return true;

                }

                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


        verfyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!emailVerified) {
                    //get user in auth
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification();
                    Toast.makeText(getApplicationContext(), "Please check your email", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Your email is verified", Toast.LENGTH_SHORT).show();
                }
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sign out
                mAuth.signOut();

                Intent intent = new Intent(woofprofile_viewProfile.this, login.class);
                Toast.makeText(getApplicationContext(), "Sucessfully Log out from the account", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        toUpdateOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofprofile_viewProfile.this,woofprofile_updateProfile.class);

                startActivity(intent);
            }
        });

    }


    public void navigateToActivityLogin()
    {
        Intent intent = new Intent(woofprofile_viewProfile.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}