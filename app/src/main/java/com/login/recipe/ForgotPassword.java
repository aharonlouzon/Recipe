package com.login.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;
import android.app.ProgressDialog;

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private ProgressDialog progressDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final MyApplication app = ((MyApplication)getApplicationContext());

        progressDialog = new ProgressDialog(this);
        reset = (Button)findViewById(R.id.reset_password_button);
        email = (EditText)findViewById(R.id.email_forgot_password);
        firebaseAuth = FirebaseAuth.getInstance();

        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                email.setText("");
                return false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                String text = email.getText().toString().trim();

                // if(!text.isEmpty() && !(text.equals("EnterEmail"))) {
                //     firebaseAuth.sendPasswordResetEmail(text);
                //     Toast.makeText(ForgotPassword.this, "Password Reset Mail was sent to your inbox", Toast.LENGTH_SHORT);
                //     startActivity(new Intent(ForgotPassword.this, MainActivity.class));

                if(!text.isEmpty() && !(text == "EnterEmail")) {
                    String response = null;
                    try {
                        progressDialog.setMessage("Cooking...");
                        progressDialog.show();
                        response = (String) new DatabaseServiceTask("forgotPassword", app).execute(text).get();
                    }
                    catch (ExecutionException | InterruptedException e) {
                        Toast.makeText(ForgotPassword.this, "Failed to email password", Toast.LENGTH_SHORT);
                    }
                    if (response.equals("error"))
                        Toast.makeText(ForgotPassword.this, "Error connecting to database", Toast.LENGTH_SHORT);
                    if (response == null)
                        Toast.makeText(ForgotPassword.this, "User doesn't exist", Toast.LENGTH_SHORT);
                        else {
                            Toast.makeText(ForgotPassword.this, "Password was sent to your inbox", Toast.LENGTH_SHORT);
                            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                        }
                    }
                }
        });
    }
}
