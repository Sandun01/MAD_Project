package com.example.mad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceGroup;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class login_activity extends AppCompatActivity {

    Button loginBtn;
    TextView forgetpwd,register;
    EditText email, pwd;
    ProgressDialog loadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        //session management for admin
        SessionManagement sessionManagement = new SessionManagement(login_activity.this);
        String isLoginUname = sessionManagement.getSession();

        //if user already login
        if(mAuth.getCurrentUser() != null)
        {
            //navigate to home
            navigateToActivityHome();
        }

        else if(!isLoginUname.equals("E")){
            //user logged in navigate to Admin home
            navigateToActivityAdmin();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        //initializing id's
        loginBtn = findViewById(R.id.login_btnlogin);
        forgetpwd = findViewById(R.id.login_forgetpassword);
        email = findViewById(R.id.login_email);
        pwd = findViewById(R.id.login_password);
        register = findViewById(R.id.login_btn_register);

        loadingBar = new ProgressDialog(login_activity.this);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onResume() {

        super.onResume();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //display prgresss bar to user
                loadingBar.setTitle("Login User");
                loadingBar.setMessage("Please Wait while Validate the Details");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                //get input details
                String emailInput = email.getText().toString();
                String pwdInput = pwd.getText().toString();

                //check valid email
                if(checkemail())
                {
                    //check login
                    mAuth.signInWithEmailAndPassword(emailInput,pwdInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Login success",Toast.LENGTH_SHORT).show();
                                //Navigate to admin home
                                loadingBar.dismiss();
                                navigateToActivityHome();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });

                }
                else
                {
                    if(emailInput.equals("admin") && pwdInput.equals("admin")) {
                        //login Session
                        Admin admin = new Admin("Admin", "Admin");

                        SessionManagement sessionManagement = new SessionManagement(login_activity.this);
                        sessionManagement.saveSession(admin);
                        Toast.makeText(getApplicationContext(), "Admin Login success",Toast.LENGTH_SHORT).show();

                        //Navigate to admin home
                        loadingBar.dismiss();
                        navigateToActivityAdmin();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Username or Password is Incorrect",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                }

            }
        });

        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               navigateToforgetpassword();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivityRegister();
            }
        });

    }

    public boolean checkemail()
    {
        String emailInput = email.getText().toString();
        String EmalFormat = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if(Pattern.compile(EmalFormat).matcher(emailInput).matches())
        {
            return true;
        }
        else {
            return false;
        }

    }

    public void navigateToActivityHome()
    {
        Intent intent = new Intent(login_activity.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public void navigateToforgetpassword()
    {
        Intent intent = new Intent(login_activity.this, user_forgetpassword.class);
        startActivity(intent);
    }

}