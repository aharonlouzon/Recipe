package com.login.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class RecipePage extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 3;
    private ListView ingredients;
    private ListView instructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingredients = findViewById(R.id.ingredients_list);
        instructions = findViewById(R.id.instructions_list);

        setContentView(R.layout.activity_recipe_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MyApplication app = ((MyApplication)getApplicationContext());
        Recipe recipe = app.getRecipe();
        TextView title = findViewById(R.id.recipe_page_title);
        title.setText(recipe.getName());

        FloatingActionButton fab = findViewById(R.id.recipe_page_add_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipePage.this, UploadRecipeImage.class));
            }
        });

        Map<String, String> recipeIngredients = recipe.getIngredients();
        ArrayList<String> recipeInstructions = recipe.getInstructions();

        ArrayList<String> ingredientsList = new ArrayList<>();

        for (Map.Entry<String,String> entry : recipeIngredients.entrySet()) {
            ingredientsList.add(entry.getKey() + entry.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientsList);

        ingredients.setAdapter(adapter);
    }
}
