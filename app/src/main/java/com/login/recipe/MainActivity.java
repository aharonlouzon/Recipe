package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import android.content.SharedPreferences;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button login;
    private Button register;
    private Button forgot_password;
    private MyApplication app;
    private SharedPreferences sharedpreferences;
    private static final String preferences = "recipeAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText)findViewById(R.id.email_input);
        password = (EditText)findViewById(R.id.password_input);
        login = (Button)findViewById(R.id.login_button);
        register = (Button)findViewById(R.id.register_button);
        progressDialog = new ProgressDialog(this);
        Button forgot_password = findViewById(R.id.forgot_password);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

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
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });

        String email = sharedpreferences.getString("Email", null);
        String password = sharedpreferences.getString("Password", null);
        if (email != null && password != null)
            validate(email, password);
    }

    private void validate(String email, String password){

        // firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        //     @SuppressLint("ShowToast")
        //     @Override
        //     public void onComplete(@NonNull Task<AuthResult> task) {
        //         if(task.isSuccessful())
        //             startActivity(new Intent(MainActivity.this, HomePage.class));
        //         else
        //             Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT);

        //     }
        // });

        Object user = null;
        try {
            progressDialog.setMessage("Cooking...");
            progressDialog.show();
            user = new DatabaseServiceTask("validateSignIn", app).execute(email, password).get();
        }
        catch (ExecutionException | InterruptedException e) {
            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT);
        }
        if (user == null){
            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT);
            return;
        }
        if (user.getClass().equals(UserProfile.class)) {
            app.setUser((UserProfile) user);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("Email", email);
            editor.putString("Password", password);
            editor.commit();
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }
        else
            Toast.makeText(MainActivity.this, "Error connecting to database", Toast.LENGTH_SHORT);
        }
}
