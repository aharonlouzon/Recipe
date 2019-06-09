package com.login.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MyArea extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ImageView avatar;
    private TextView userName;
    private TextView cookingSkills;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_area);


        firebaseAuth = FirebaseAuth.getInstance();

        userName = (TextView)findViewById(R.id.name_my_area);
        cookingSkills = (TextView)findViewById(R.id.cooking_skills_my_area);

        avatar = (ImageView)findViewById(R.id.imageView_my_area);
        avatar.setImageResource(R.drawable.male_avatar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyArea.this, AddRecipe.class));
            }
        });
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
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MyArea.this, MainActivity.class));
            }
            case R.id.my_area_button_user_menu: {
                startActivity(new Intent(MyArea.this, MyArea.class));
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
