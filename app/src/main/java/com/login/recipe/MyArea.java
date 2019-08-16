package com.login.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MyArea extends AppCompatActivity {

    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_area);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        avatar = (ImageView)findViewById(R.id.imageView_my_area);
        avatar.setImageResource(R.drawable.male_avatar);

        FloatingActionButton fab = findViewById(R.id.recipe_page_add_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyArea.this, AddRecipe.class));
            }
        });
    }

}
