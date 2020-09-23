package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login_activity extends AppCompatActivity {

    Button loginBtn;
    TextView forgetpwd,register;
    EditText un,pwd;

    @Override
    protected void onStart() {
        super.onStart();

        //check user is logged in
        SessionManagement sessionManagement = new SessionManagement(login_activity.this);
        String isLoginUname = sessionManagement.getSession();

        if(!isLoginUname.equals("E")){
            //user logged in navigate to Admin home
            navigateToActivityAdmin();
        }

    }

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

                String unameInput = un.getText().toString();
                String pwdInput = pwd.getText().toString();

                if(unameInput.equals("admin") && pwdInput.equals("admin")) {
                    //login Session
                    Admin admin = new Admin("Admin", "Admin");

                    SessionManagement sessionManagement = new SessionManagement(login_activity.this);
                    sessionManagement.saveSession(admin);
                    Toast.makeText(getApplicationContext(), "Admin Login success",Toast.LENGTH_SHORT).show();

                    //Navigate to admin home
                    navigateToActivityAdmin();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Username or Password is Incorrect",Toast.LENGTH_SHORT).show();
                }

            }
        });

        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               navigateToActivitySecond();
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void navigateToActivityRegister()
    {
        Intent intent = new Intent(login_activity.this, Registratiion.class);
        startActivity(intent);
    }

}