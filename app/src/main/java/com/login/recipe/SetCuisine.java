package com.login.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetCuisine extends AppCompatActivity {

    private CheckBox asian;
    private CheckBox middle_eastern;
    private CheckBox italian;
    private CheckBox european;
    private CheckBox baking;
    private CheckBox meat;
    private Button continue_button;
    private UserProfile user;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_cuisine);
        Intent intent = getIntent();
        user = (UserProfile)intent.getSerializableExtra("user");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseUser != null)
            userId = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference(userId);

        asian = (CheckBox)findViewById(R.id.asian_check_set_cuisine);
        middle_eastern = (CheckBox)findViewById(R.id.middle_eastern_checkbox_set_cuisine);
        italian = (CheckBox)findViewById(R.id.italian_checkbox_set_cuisine);
        european = (CheckBox)findViewById(R.id.european_checkbox_set_cuisine);
        baking = (CheckBox)findViewById(R.id.baking_checkbox_set_cuisine);
        meat = (CheckBox)findViewById(R.id.meat_checkbox_set_cuisine);
        continue_button = (Button)findViewById(R.id.continue_button_set_cuisine);

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
                    //falls here
                    databaseReference.setValue(user);
                    startActivity(new Intent(SetCuisine.this, HomePage.class));
            }
        });
    }
}
