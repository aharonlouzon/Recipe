package com.login.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private ProgressDialog progressDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final MyApplication app = ((MyApplication) getApplicationContext());

        progressDialog = new ProgressDialog(this);
        Button reset = findViewById(R.id.reset_password_button);
        email = findViewById(R.id.email_forgot_password);

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
                progressDialog.setMessage("Cooking...");
                progressDialog.show();
                String text = email.getText().toString().trim();

                if (!text.isEmpty() && !(text.equals("EnterEmail"))) {
                    Object response;
                    try {
                        response = new DatabaseServiceTask("forgotPassword", app).execute(text).get();
                        startActivity(new Intent(ForgotPassword.this, MainActivity.class));

                        if (!(response instanceof String))
                            if (response instanceof Exception)
                                throw (Exception) response;

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(ForgotPassword.this, "Failed to email password",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                    }
                }
            }
        });
    }
}
