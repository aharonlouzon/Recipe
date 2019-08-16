package com.login.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecipePage extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MyApplication app = ((MyApplication)getApplicationContext());
        Recipe recipe = app.getRecipe();
        TextView title = findViewById(R.id.recipe_title_recipe_page);
        title.setText(recipe.getName());

        FloatingActionButton fab = findViewById(R.id.recipe_page_add_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipePage.this, UploadRecipeImage.class));
            }
        });
    }
}
