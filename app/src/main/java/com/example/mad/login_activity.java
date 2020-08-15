package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class login_activity extends AppCompatActivity {

    Button loginBtn;
    TextView forgetpwd,register;
    EditText un,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        loginBtn = findViewById(R.id.login_btnlogin);
        forgetpwd = findViewById(R.id.login_forgetpassword);
        un = findViewById(R.id.login_username);
        pwd = findViewById(R.id.login_password);
        register = findViewById(R.id.login_btn_register);
    }

    @Override
    protected void onResume() {

        super.onResume();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivitySecond();
            }
        });

        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               navigateToActivityAdmin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivityRegister();
            }
        });

    }


    public void navigateToActivitySecond()
    {
        Intent intent = new Intent(login_activity.this, Home.class);
        startActivity(intent);
    }

    public void navigateToActivityAdmin()
    {
        Intent intent = new Intent(login_activity.this, woofadmin_menu.class);
        startActivity(intent);
    }

    public void navigateToActivityRegister()
    {
        Intent intent = new Intent(login_activity.this, ragistratiion.class);
        startActivity(intent);
    }

}