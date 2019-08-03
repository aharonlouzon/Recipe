package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;

//import android.support.annotation.NonNull;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class RegiterPage extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private Button sign_up;
    private Button alredy_user;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
//    private DatabaseService databaseService;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter_page);

        firebaseAuth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.email_input);
        password = (EditText)findViewById(R.id.password_input);
        confirm_password = (EditText)findViewById(R.id.confirm_password_input);
        sign_up = (Button)findViewById(R.id.sign_up_button);
        alredy_user = (Button)findViewById(R.id.already_user_button);
        progressDialog = new ProgressDialog(this);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();
                progressDialog.setMessage("Cooking...");
                progressDialog.show();
                Intent intent = new Intent(RegiterPage.this, SetUser.class);
                intent.putExtra("password", user_password);
                intent.putExtra("email", user_email);
                startActivity(intent);

//                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || !password.getText().toString().trim().equals(confirm_password.getText().toString().trim())) {
//                    Toast.makeText(RegiterPage.this, "Invalid Input", Toast.LENGTH_SHORT);
//                }
//                else
//                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()) {
//                                Toast.makeText(RegiterPage.this, "Registretion successful", Toast.LENGTH_SHORT);
//                                startActivity(new Intent(RegiterPage.this, SetUser.class));
//                            }
//                            else
//                                Toast.makeText(RegiterPage.this, "Registretion failed", Toast.LENGTH_SHORT);
//
//                        }
//                    });
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
}
