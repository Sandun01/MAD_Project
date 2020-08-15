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
    TextView forgetpwd;
    EditText un,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        loginBtn = findViewById(R.id.login_btnlogin);
        forgetpwd = findViewById(R.id.login_forgetpassword);
        un = findViewById(R.id.login_username);
        pwd = findViewById(R.id.login_password);
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

    }

//    private void checkAdminLogin() {
//
//        String username = un.getText().toString();
//        String password = pwd.getText().toString();
//
//        if(username == "admin" && password == "admin" )
//        {
//            navigateToActivityAdmin();
//        }
//    }

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


}