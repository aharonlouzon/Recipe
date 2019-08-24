package com.login.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AddRecipe extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private CheckBox asian;
    private CheckBox middle_eastern;
    private CheckBox italian;
    private CheckBox european;
    private CheckBox baking;
    private CheckBox meat;
    private Button addDirection;
    private EditText product_name;
    private EditText quantity;
    private EditText title;
    private EditText direction_step;
    private Recipe recipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        progressDialog = new ProgressDialog(this);
        asian = findViewById(R.id.asian_rec_category);
        middle_eastern = findViewById(R.id.middle_eastern_rec_category);
        italian = findViewById(R.id.italian_rec_category);
        european = findViewById(R.id.european_rec_categories);
        baking = findViewById(R.id.baking_rec_category);
        meat = findViewById(R.id.meat_rec_category);
        Button continueButton = findViewById(R.id.continue_button_add_recipe);
        addDirection = findViewById(R.id.add_recipe_direction_num);
        FloatingActionButton addIngredient = findViewById(R.id.add_ingredient_button);
        product_name = findViewById(R.id.add_recipe_product);
        direction_step = findViewById(R.id.direction_text_add_recipe);
        quantity = findViewById(R.id.add_recipe_Quantity);
        title = findViewById(R.id.title_add_recipe);

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
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cooking...");
                progressDialog.show();

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

                //add the recipe to the database
                String response = null;
                try {
                    response = (String) new DatabaseServiceTask("addRecipe", app).execute(recipe).get();
                }
                catch (ExecutionException | InterruptedException e) {
                    Toast.makeText(AddRecipe.this, "Failed to add new Recipe", Toast.LENGTH_SHORT);
                }
                if (response == null)
                    Toast.makeText(AddRecipe.this, "Failed to add new Recipe", Toast.LENGTH_SHORT);
                else if (response.equals("error"))
                    Toast.makeText(AddRecipe.this, "Error connecting to database", Toast.LENGTH_SHORT);
                else {
                    Toast.makeText(AddRecipe.this, "Recipe Added", Toast.LENGTH_SHORT);
                    app.setRecipe(recipe);
                    startActivity(new Intent(AddRecipe.this, RecipePage.class));
                }
            }
        });

    }
}
