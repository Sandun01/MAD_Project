package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.mainclasses.SessionManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class woofadmin_account extends AppCompatActivity {

    ImageView logo;
    Button logoutBtn;
    TextView uname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_account);

        //Initializing id's
        logo = findViewById(R.id.app_logo_top);
        logoutBtn = findViewById(R.id.admin_logoutbtn);
        uname = findViewById(R.id.view_username);

        SessionManagement sessionManagement = new SessionManagement(woofadmin_account.this);
        String Uname = sessionManagement.getSession();
        uname.setText(Uname);


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


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(woofadmin_account.this, woofadmin_menu.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove session
                SessionManagement sessionManagement = new SessionManagement(woofadmin_account.this);
                sessionManagement.removeSession();
                navigateToLogin();
            }
        });

    }

    public void navigateToLogin()
    {
        Intent intent = new Intent(woofadmin_account.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}