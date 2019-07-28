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
import java.io.IOException;

public class ForgotPassword extends AppCompatActivity {

    private Button reset;
    private EditText email;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final MyApplication app = ((MyApplication)getApplicationContext());
        final DatabaseService database = app.getDatabase();

        reset = (Button)findViewById(R.id.reset_password_button);
        email = (EditText)findViewById(R.id.email_forgot_password);

        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                email.setText("");
                return false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = email.getText().toString().trim();
                if(!text.isEmpty() && !(text == "EnterEmail")) {
                    try {
                        String response = database.forgotPassword(text);
                        if (response != null) {
                            Toast.makeText(ForgotPassword.this, "Password was sent to your inbox", Toast.LENGTH_SHORT);
                            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                        }
                        else
                            Toast.makeText(ForgotPassword.this, "Login failed", Toast.LENGTH_SHORT);
                    }
                    catch (IOException e) {
                        Toast.makeText(ForgotPassword.this, "Error connecting to database", Toast.LENGTH_SHORT);
                    }

                }
            }
        });
    }
}
