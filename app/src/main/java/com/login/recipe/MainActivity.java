package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private ProgressDialog progressDialog;
    private MyApplication app;
    private SharedPreferences sharedpreferences;
    private static final String preferences = "recipeAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        Button login = findViewById(R.id.login_button);
        Button register = findViewById(R.id.register_button);
        progressDialog = new ProgressDialog(this);
        Button forgot_password = findViewById(R.id.forgot_password);

        app = ((MyApplication)getApplicationContext());
        sharedpreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);

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
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });

        String get_email = sharedpreferences.getString("Email", null);
        String get_password = sharedpreferences.getString("Password", null);
        if (get_email != null && get_password != null)
            try {
                validate(get_email, get_password);
            }catch (Exception e){
                Toast toast = Toast.makeText(MainActivity.this, "Please log in", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
    }

    @SuppressLint("ShowToast")
    private void validate(String email, String password) {

        UserProfile user = null;
        progressDialog.setMessage("Cooking...");
        progressDialog.show();
        try {
            user = (UserProfile) new DatabaseServiceTask("validateSignIn", app).execute(email, password).get();
            if (user == null)
                throw new Exception();
            app.setUser(user);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("Email", email);
            editor.putString("Password", password);
            editor.apply();
            progressDialog.dismiss();
            startActivity(new Intent(MainActivity.this, HomePage.class));
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast toast = Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
