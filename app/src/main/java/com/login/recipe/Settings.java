package com.login.recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    private MyApplication app;
    private ProgressDialog progressDialog;
    private UserProfile user;
    private EditText firstName;
    private EditText lastName;
    private EditText country;
    private EditText newPassword;
    private  EditText passwordConfirm;
    private RadioGroup radioGroup;
    private CheckBox asian;
    private CheckBox middle_eastern;
    private CheckBox italian;
    private CheckBox european;
    private CheckBox baking;
    private CheckBox meat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = ((MyApplication)getApplicationContext());
        user = app.getUser();
        progressDialog = new ProgressDialog(this);

        // commit changes button
        Button commitChanges = findViewById(R.id.settings_button_change);

        // get edittext views
        lastName = findViewById(R.id.settings_name_last);
        firstName = findViewById(R.id.settings_name_first);
        country = findViewById(R.id.settings_country2);
        passwordConfirm = findViewById(R.id.settings_confirm);
        newPassword = findViewById(R.id.settings_password);

        // get checkboxes
        radioGroup = findViewById(R.id.settings_radio_group_skills);
        asian = findViewById(R.id.settings_asian_rec_category);
        middle_eastern = findViewById(R.id.settings_middle_eastern_rec_category);
        italian = findViewById(R.id.settings_italian_rec_category);
        european  = findViewById(R.id.settings_european_rec_categories) ;
        baking = findViewById(R.id.settings_baking_rec_category);
        meat = findViewById(R.id.settings_meat_rec_category);

        // set defaults
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        country.setText(user.getCountry());

        int skill;
        if (user.getCookingSkills() == UserProfile.skillLevel.BEGINNER)
            skill = R.id.settings_begginer_radio_set_user;
        else if (user.getCookingSkills() == UserProfile.skillLevel.INTERMEDIATE)
            skill = R.id.settings_intermediate_set_user;
        else
            skill = R.id.settings_experienced_set_user;

        radioGroup.check(skill);

        if(user.getCuisines().contains("asian"))
            asian.setChecked(true);
        if(user.getCuisines().contains("middle_eastern"))
            middle_eastern.setChecked(true);
        if(user.getCuisines().contains("italian"))
            italian.setChecked(true);
        if(user.getCuisines().contains("european"))
            european.setChecked(true);
        if(user.getCuisines().contains("baking"))
            baking.setChecked(true);
        if(user.getCuisines().contains("meat"))
            meat.setChecked(true);


        commitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserProfile.skillLevel cookingSkills = null;
                ArrayList<String> cuisines = new ArrayList<>();

                if(asian.isChecked())
                    cuisines.add("asian");
                if(middle_eastern.isChecked())
                    cuisines.add("middle_eastern");
                if(italian.isChecked())
                    cuisines.add("italian");
                if(european.isChecked())
                    cuisines.add("european");
                if(baking.isChecked())
                    cuisines.add("baking");
                if(meat.isChecked())
                    cuisines.add("meat");

                //noinspection ToArrayCallWithZeroLengthArrayArgument
                String[] cuisinesArray = cuisines.toArray(new String[cuisines.size()]);

                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.settings_begginer_radio_set_user:
                        cookingSkills = UserProfile.skillLevel.BEGINNER;
                        break;
                    case R.id.settings_intermediate_set_user:
                        cookingSkills = UserProfile.skillLevel.INTERMEDIATE;
                        break;
                    case R.id.settings_experienced_set_user:
                        cookingSkills = UserProfile.skillLevel.PRO;
                        break;
                }

                // get new password
                String password = null;
                if (!newPassword.getText().toString().isEmpty())
                    if (newPassword.getText().toString().trim().equals(passwordConfirm.getText().toString().trim()))
                        password = newPassword.getText().toString().trim();
                    else {
                        Toast toast = Toast.makeText(Settings.this, "Password don't match", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }

                updateUser(password,
                           firstName.getText().toString(),
                           lastName.getText().toString(),
                           country.getText().toString(),
                           cuisinesArray,
                           cookingSkills);

            }
        });

    }

    public void updateUser(String password, String newFirstName, String newLastName,
                      String newCountry, String[] newCuisines, UserProfile.skillLevel newSkillLeve){

        progressDialog.setMessage("Cooking...");
        progressDialog.show();
        Object results;

        try {
            results = new DatabaseServiceTask(
                    "updateUserProfile",
                              app).execute(
                                      user.getEmail(),
                                      password,
                                      newFirstName,
                                      newLastName,
                                      newCountry,
                                      newCuisines,
                                      newSkillLeve).get();

            if (!(results instanceof UserProfile))
                if (results instanceof Exception)
                    throw (Exception) results;

            UserProfile UpdatedUser = null;
            if (results instanceof UserProfile)
                UpdatedUser = (UserProfile) results;

            app.setUser(UpdatedUser);
            Toast toast = Toast.makeText(Settings.this, "User updated successfully", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            app.setIsMyArea(true);
            startActivity(new Intent(Settings.this, MyArea.class));
        }catch (Exception e){
            Toast toast = Toast.makeText(Settings.this, "Failed to update user", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout_button: {
                finish();
                startActivity(new Intent(Settings.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                app.setIsMyArea(true);
                startActivity(new Intent(Settings.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                app.setHome(true);
                startActivity(new Intent(Settings.this, HomePage.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
