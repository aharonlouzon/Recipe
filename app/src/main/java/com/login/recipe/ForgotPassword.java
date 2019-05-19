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

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private Button reset;
    private EditText email;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

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
            @Override
            public void onClick(View v) {
                String text = email.getText().toString().trim();
                if(!text.isEmpty() && !(text == "EnterEmail")) {
                    firebaseAuth.sendPasswordResetEmail(text);
                    Toast.makeText(ForgotPassword.this, "Password Reset Mail was sent to your inbox", Toast.LENGTH_SHORT);
                    startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                }
            }
        });
    }
}
