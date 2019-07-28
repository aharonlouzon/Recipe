package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegisterPage extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private Button sign_up;
    private Button already_user;
    private ProgressDialog progressDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter_page);

        final MyApplication app = ((MyApplication)getApplicationContext());

        app.setUser(new UserProfile());
        email = (EditText)findViewById(R.id.email_input);
        password = (EditText)findViewById(R.id.password_input);
        confirm_password = (EditText)findViewById(R.id.confirm_password_input);
        sign_up = (Button)findViewById(R.id.sign_up_button);
        already_user = (Button)findViewById(R.id.already_user_button);
        progressDialog = new ProgressDialog(this);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();
                progressDialog.setMessage("Cooking...");
                progressDialog.show();
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || !password.getText().toString().trim().equals(confirm_password.getText().toString().trim())) {
                    Toast.makeText(RegisterPage.this, "Invalid Input", Toast.LENGTH_SHORT);
                }
                else {
                    app.getUser().setEmail(user_email);
                    app.setNewPassword(user_password);
                    startActivity(new Intent(RegisterPage.this, SetUser.class));
                }
            }
        });

        already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
