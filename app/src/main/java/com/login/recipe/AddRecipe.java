package com.login.recipe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

public class AddRecipe extends AppCompatActivity {

    private CheckBox asian;
    private CheckBox middle_eastern;
    private CheckBox italian;
    private CheckBox european;
    private CheckBox baking;
    private CheckBox meat;
    private FloatingActionButton addIngredient;
    private Button addDirection;
    private Button continueButton;
    private EditText product_name;
    private EditText quantity;
    private EditText title;
    private EditText direction_step;
    private Recipe recipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        asian = (CheckBox)findViewById(R.id.asian_rec_category);
        middle_eastern = (CheckBox)findViewById(R.id.middle_eastern_rec_category);
        italian = (CheckBox)findViewById(R.id.italian_rec_category);
        european = (CheckBox)findViewById(R.id.european_rec_categories);
        baking = (CheckBox)findViewById(R.id.baking_rec_category);
        meat = (CheckBox)findViewById(R.id.meat_rec_category);
        continueButton = (Button)findViewById(R.id.continue_button_add_recipe);
        addDirection = (Button)findViewById(R.id.add_recipe_direction_num);
        addIngredient = (FloatingActionButton) findViewById(R.id.add_ingredient_button);
        product_name = (EditText)findViewById(R.id.add_recipe_product);
        direction_step = (EditText)findViewById(R.id.direction_text_add_recipe);
        quantity = (EditText)findViewById(R.id.add_recipe_Quantity);
        title = (EditText)findViewById(R.id.title_add_recipe);

        addDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(direction_step.getText().toString().trim().isEmpty())
                    return;
                String step = direction_step.getText().toString();
                int step_num = Integer.parseInt(addDirection.getText().toString());
                step_num++;
                addDirection.setText(String.valueOf(step_num));
                recipe.getInstructions().add(step);
                direction_step.getText().clear();
                return;
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product_name.getText().toString().trim().isEmpty() || quantity.getText().toString().trim().isEmpty())
                    return;
                String name = product_name.getText().toString().trim();
                String quan = quantity.getText().toString().trim();
                recipe.getIngredients().put(name,quan);
                product_name.getText().clear();
                quantity.getText().clear();
                return;
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().isEmpty())
                    return;
                if(recipe.getInstructions().size() == 0)
                    return;
                if(recipe.getIngredients().size() == 0)
                    return;
                if(asian.isChecked())
                    recipe.getCuisines().add("asian");
                if(middle_eastern.isChecked())
                    recipe.getCuisines().add("middle_eastern");
                if(italian.isChecked())
                    recipe.getCuisines().add("italian");
                if(european.isChecked())
                    recipe.getCuisines().add("european");
                if(baking.isChecked())
                    recipe.getCuisines().add("baking");
                if(meat.isChecked())
                    recipe.getCuisines().add("meat");

                //set author to be current user
                MyApplication app = ((MyApplication)getApplicationContext());
                recipe.setAuthor(app.getUser().getEmail());

                //set release time for recipe
                recipe.setReleaseDate(new Date(System.currentTimeMillis()));

                recipe.setName(title.getText().toString().trim());

                // add the recipe to the database
                DatabaseService database = app.getDatabase();
                try {
                    String response = database.addRecipe(recipe);
                    if (response == null)
                        Toast.makeText(AddRecipe.this, "Failed to add new Recipe", Toast.LENGTH_SHORT);
                    else {
                        Toast.makeText(AddRecipe.this, "Recipe added", Toast.LENGTH_SHORT);
                        startActivity(new Intent(AddRecipe.this, MyArea.class));
                    }
                }
                catch (IOException e) {
                    Toast.makeText(AddRecipe.this, "Error connecting to database", Toast.LENGTH_SHORT);
                }


            }
        });

    }
}
