package com.login.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SetUser extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private RadioGroup radioGroup;
    private RadioButton radioButtonBeginner;
    private RadioButton radioButtonIntermediate;
    private RadioButton radioButtonPro;
    private EditText country;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstName = (EditText) findViewById(R.id.first_name_set_user);
        lastName = (EditText)findViewById(R.id.last_name_text_set_user);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group_set_user);
        country = (EditText)findViewById(R.id.country_text_set_user);
        continueButton = (Button)findViewById(R.id.continue_button_set_user);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = firstName.getText().toString().trim();
                String last_name = lastName.getText().toString().trim();
                UserProfile.skillLevel cookingSkills = UserProfile.skillLevel.BEGINNER;
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.begginer_radio_set_user:
                        cookingSkills = UserProfile.skillLevel.BEGINNER;
                        break;
                    case R.id.intermediate_set_user:
                        cookingSkills = UserProfile.skillLevel.INTEMEDIATE;
                        break;
                    case R.id.experienced_set_user:
                        cookingSkills = UserProfile.skillLevel.PRO;
                        break;
                }
                String country_input = country.getText().toString().trim();

                UserProfile user = new UserProfile(first_name, last_name, cookingSkills, country_input);
                Intent intent = new Intent(SetUser.this, SetCuisine.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

    }

}
