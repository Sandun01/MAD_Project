package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Registratiion extends AppCompatActivity {

    //creating variables
    Button btnReg;
    TextView navLogin;
    EditText unameInput, pwd1Input, pwd2input, phoneInput, emailInput;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registratiion);

        //initializing id's
        unameInput = findViewById(R.id.username);
        phoneInput = findViewById(R.id.phoneNumber);
        pwd1Input = findViewById(R.id.password1);
        pwd2input = findViewById(R.id.password2);
        emailInput = findViewById(R.id.email);

        btnReg = findViewById(R.id.btn_crate_account);
        navLogin = findViewById(R.id.btn_navlogin);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //create user account
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check fields are empty
                if( TextUtils.isEmpty(unameInput.getText().toString()) ){
                    Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                }
                else if( TextUtils.isEmpty(phoneInput.getText().toString()) ){
                    Toast.makeText(getApplicationContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
                }
                else if( TextUtils.isEmpty(emailInput.getText().toString()) ){
                    Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                }
                else if( TextUtils.isEmpty(pwd1Input.getText().toString()) ){
                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                }
                else if( TextUtils.isEmpty(pwd2input.getText().toString()) ){
                    Toast.makeText(getApplicationContext(), "Please confirm Password", Toast.LENGTH_SHORT).show();
                }
                else{

                    //check phone number
                    boolean chkPhone = checkphonenumber();

                    //check email
                    boolean chkEmail = checkemail();

                    //check passwords are matching
                   boolean chkPwd = checkPasswords();

                   if(chkPhone && chkEmail && chkPwd)
                   {
                       String uname = unameInput.getText().toString();
                       String pwd = pwd1Input.getText().toString();
                       String email = emailInput.getText().toString();
                       String phone = phoneInput.getText().toString();

                       //add data to the database
                       boolean checkAdded = addUserDetailsToDatabase(uname, phone, email, pwd);

                       //if data added sucessfully
                       if (checkAdded){
                           Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();

                           //navigate user to home page
                           navigateToHome();
                       }

                   }

                }

            }
        });

        //already have an accout navigate to login
        navLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLogin();
            }
        });

    }

    public boolean checkPasswords()
    {
        String pwd1 = pwd1Input.getText().toString();
        String pwd2 = pwd2input.getText().toString();

        if(pwd1.equals(pwd2))
        {
            return true;
        }

        else if(pwd1.length() < 8){
            Toast.makeText(getApplicationContext(), "Passwords length must grater than or equal to 8", Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            Toast.makeText(getApplicationContext(), "Passwords are Not matching", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean checkphonenumber()
    {
        String phone = phoneInput.getText().toString();

        if(phone.length() == 10)
        {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean checkemail()
    {
        String email = emailInput.getText().toString();
        String EmalFormat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if(Pattern.compile(EmalFormat).matcher(email).matches())
        {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private boolean addUserDetailsToDatabase(String uname, String phone, String email, String pwd) {

        try{
            User user = new User();

            //get firebase database instance
            dbref = FirebaseDatabase.getInstance().getReference().child("User");

            //initialize values
            user.setUsername(uname);
            user.setPassword(pwd);
            user.setEmail(email);
            user.setPhone(phone);

            //add to database
            dbref.push().setValue(user);

            //display message to user
            Toast.makeText(getApplicationContext(), "Successfully Created User", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }

    }

    public void navigateToLogin()
    {
        Intent intent = new Intent(Registratiion.this, login_activity.class);
        startActivity(intent);
    }

    public void navigateToHome()
    {
        Intent intent = new Intent(Registratiion.this, Home.class);
        startActivity(intent);
    }

}