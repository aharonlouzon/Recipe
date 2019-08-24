package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class SetCuisine extends AppCompatActivity {

    private CheckBox asian;
    private CheckBox middle_eastern;
    private CheckBox italian;
    private CheckBox european;
    private CheckBox baking;
    private CheckBox meat;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedpreferences;
    private static final String preferences = "recipeAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_cuisine);
        progressDialog = new ProgressDialog(this);

        asian = findViewById(R.id.asian_rec_category);
        middle_eastern = findViewById(R.id.middle_eastern_rec_category);
        italian = findViewById(R.id.italian_rec_category);
        european = findViewById(R.id.european_rec_categories);
        baking = findViewById(R.id.baking_rec_category);
        meat = findViewById(R.id.meat_rec_category);
        Button continue_button = findViewById(R.id.continue_button_add_recipe);

        final MyApplication app = ((MyApplication)getApplicationContext());
        final UserProfile user = app.getUser();
        sharedpreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
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
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("Email", user.getEmail());
                    editor.putString("Password", app.getNewPassword());
                    editor.apply();
                    Toast.makeText(SetCuisine.this, "Registration Successful", Toast.LENGTH_SHORT);
                    startActivity(new Intent(SetCuisine.this, HomePage.class));
                }

            }
        });
    }
}
