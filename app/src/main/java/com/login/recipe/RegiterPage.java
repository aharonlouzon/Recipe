package com.login.recipe;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegiterPage extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private Button sign_up;
    private Button alredy_user;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter_page);

        firebaseAuth = FirebaseAuth.getInstance();

        name = (EditText)findViewById(R.id.name_input);
        email = (EditText)findViewById(R.id.email_input);
        password = (EditText)findViewById(R.id.password_input);
        confirm_password = (EditText)findViewById(R.id.confirm_password_input);
        sign_up = (Button)findViewById(R.id.sign_up_button);
        alredy_user = (Button)findViewById(R.id.already_user_button);

//        name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name.setText("");
//            }
//        });
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                name.setText("");
                refillFields(name);
                return false;
            }
        });
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                email.setText("");
                refillFields(email);
                return false;
            }
        });
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                password.setText("");
                refillFields(password);
                return false;
            }
        });
        confirm_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                confirm_password.setText("");
                refillFields(confirm_password);
                return false;
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegiterPage.this, "Registretion successful", Toast.LENGTH_SHORT);
                            startActivity(new Intent(RegiterPage.this, MainActivity.class));
                        }
                        else
                            Toast.makeText(RegiterPage.this, "Registretion failed", Toast.LENGTH_SHORT);

                    }
                });
            }
        });
        alredy_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegiterPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean create_user(String name, String email, String password){
        boolean results = false;


        return results;
    }
    private void refillFields(EditText text){
        if(email.getText().toString().isEmpty() && !text.equals(email))
            email.setText("Email");
        if(name.getText().toString().isEmpty() && !text.equals(name))
            name.setText("Name");
        if(password.getText().toString().isEmpty() && !text.equals(password))
            password.setText("Password");
        if(confirm_password.getText().toString().isEmpty() && !text.equals(confirm_password))
            confirm_password.setText("Confirm Password");
    }
}
