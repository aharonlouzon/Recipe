package com.login.recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.login.recipe.DatabaseService;

import android.widget.Toast;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import android.app.ProgressDialog;

public class SetCuisine extends AppCompatActivity {

    private CheckBox asian;
    private CheckBox middle_eastern;
    private CheckBox italian;
    private CheckBox european;
    private CheckBox baking;
    private CheckBox meat;
    private UserProfile user;
    private DatabaseService databaseService = new DatabaseService();
    private String password;

//    private FirebaseUser firebaseUser;
//    private FirebaseDatabase firebaseDatabase;
//    private FirebaseAuth firebaseAuth;
//    private DatabaseReference databaseReference;
//    private String userId;

    private Button continue_button;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_cuisine);
        Intent intent = getIntent();
        user = (UserProfile)intent.getSerializableExtra("user");
        password = (String)intent.getSerializableExtra("password");
        progressDialog = new ProgressDialog(this);

//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        if (firebaseUser != null)
//            userId = firebaseUser.getUid();
//        databaseReference = firebaseDatabase.getReference(userId);

        asian = findViewById(R.id.asian_rec_category);
        middle_eastern = findViewById(R.id.middle_eastern_rec_category);
        italian = findViewById(R.id.italian_rec_category);
        european = findViewById(R.id.european_rec_categories);
        baking = findViewById(R.id.baking_rec_category);
        meat = findViewById(R.id.meat_rec_category);
        Button continue_button = findViewById(R.id.continue_button_add_recipe);

        final MyApplication app = ((MyApplication)getApplicationContext());
        final UserProfile user = app.getUser();

        progressDialog = new ProgressDialog(this);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cooking...");
                progressDialog.show();

                if(asian.isChecked())
                    user.addCuisine("asian");
                if(middle_eastern.isChecked())
                    user.addCuisine("middle_eastern");
                if(italian.isChecked())
                    user.addCuisine("italian");
                if(european.isChecked())
                    user.addCuisine("european");
                if(baking.isChecked())
                    user.addCuisine("baking");
                if(meat.isChecked())
                    user.addCuisine("meat");

                //add user info to database
                String response = null;
                try {
                    progressDialog.setMessage("Cooking...");
                    progressDialog.show();
                    response = (String) new DatabaseServiceTask("addUser", app).execute(user, app.getNewPassword()).get();
                }
                catch (ExecutionException | InterruptedException e) {
                    Toast.makeText(SetCuisine.this, "Failed to add new User", Toast.LENGTH_SHORT);
                }
                if (response == null)
                    Toast.makeText(SetCuisine.this, "Failed to add new User", Toast.LENGTH_SHORT);
                else if (response.equals("error"))
                    Toast.makeText(SetCuisine.this, "Error connecting to database", Toast.LENGTH_SHORT);
                else {
                    Toast.makeText(SetCuisine.this, "Registration Successful", Toast.LENGTH_SHORT);
                    startActivity(new Intent(SetCuisine.this, HomePage.class));
                }

            }
        });
    }
}
