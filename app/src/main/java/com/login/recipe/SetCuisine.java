package com.login.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SetCuisine extends AppCompatActivity {

    private CheckBox asian;
    private CheckBox middle_eastern;
    private CheckBox italian;
    private CheckBox european;
    private CheckBox baking;
    private CheckBox meat;
    private Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_cuisine);

        final MyApplication app = ((MyApplication)getApplicationContext());
        final UserProfile user = app.getUser();

        asian = (CheckBox)findViewById(R.id.asian_rec_category);
        middle_eastern = (CheckBox)findViewById(R.id.middle_eastern_rec_category);
        italian = (CheckBox)findViewById(R.id.italian_rec_category);
        european = (CheckBox)findViewById(R.id.european_rec_categories);
        baking = (CheckBox)findViewById(R.id.baking_rec_category);
        meat = (CheckBox)findViewById(R.id.meat_rec_category);
        continue_button = (Button)findViewById(R.id.continue_button_add_recipe);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
