package com.login.recipe;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SetUser extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private RadioGroup radioGroup;
    private EditText country;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MyApplication app = ((MyApplication)getApplicationContext());

        firstName = (EditText) findViewById(R.id.first_name_set_user);
        lastName = (EditText)findViewById(R.id.last_name_text_set_user);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group_set_user);
        country = (EditText)findViewById(R.id.country_text_set_user);
        progressDialog = new ProgressDialog(this);
        Button continueButton = (Button) findViewById(R.id.continue_button_set_user);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cooking...");
                progressDialog.show();
                
                String first_name = firstName.getText().toString().trim();
                String last_name = lastName.getText().toString().trim();
                UserProfile.skillLevel cookingSkills = UserProfile.skillLevel.BEGINNER;

                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.begginer_radio_set_user:
                        cookingSkills = UserProfile.skillLevel.BEGINNER;
                        break;
                    case R.id.intermediate_set_user:
                        cookingSkills = UserProfile.skillLevel.INTERMEDIATE;
                        break;
                    case R.id.experienced_set_user:
                        cookingSkills = UserProfile.skillLevel.PRO;
                        break;
                }

                String country_input = country.getText().toString().trim();
                UserProfile user = new UserProfile(first_name, last_name, cookingSkills, country_input);
                app.getUser().setFirstName(first_name);
                app.getUser().setLastName(last_name);
                app.getUser().setCookingSkills(cookingSkills);
                app.getUser().setCountry(country_input);

                Intent intent = new Intent(SetUser.this, SetCuisine.class);
                startActivity(intent);
            }
        });

    }

}
