package com.login.recipe;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button register;
    private ProgressDialog progressDialog;
    private Button forgot_password;
    private MyApplication app;
    private DatabaseService database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText)findViewById(R.id.email_input);
        password = (EditText)findViewById(R.id.password_input);
        login = (Button)findViewById(R.id.login_button);
        register = (Button)findViewById(R.id.register_button);
        progressDialog = new ProgressDialog(this);
        forgot_password = (Button)findViewById(R.id.forgot_password);

        app = ((MyApplication)getApplicationContext());
        database = app.getDatabase();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cooking...");
                progressDialog.show();
                validate(email.getText().toString(), password.getText().toString());
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });

    }

    private void validate(String email, String password){
        UserProfile user;
        try {
            user = database.validateSignIn(email, password);
            if (user != null) {
                app.setUser(user);
                startActivity(new Intent(MainActivity.this, HomePage.class));
            }
            else
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT);
        }
        catch (IOException e) {
            Toast.makeText(MainActivity.this, "Error connecting to database", Toast.LENGTH_SHORT);
        }
    }
}
