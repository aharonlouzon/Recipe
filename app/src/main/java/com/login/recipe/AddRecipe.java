package com.login.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private EditText description;
    private MyApplication app;

    // Type radio handling
    private int typeCheckedId;
    private boolean typeIsChecked = false;

    //radio buttons type
    private RadioButton dessert;
    private RadioButton soup;
    private RadioButton main;
    private RadioButton appetizer;
    private RadioButton salad;

    //skills radio button
    private RadioButton begginer;
    private RadioButton intermediate;
    private RadioButton pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //globals
        app = ((MyApplication)getApplicationContext());
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
        title = findViewById(R.id.recipe_name);
        description = findViewById(R.id.descriptions_text_add_recipe);

        //radio buttons type
        dessert = findViewById(R.id.radioButtonDessertAddRecipe);
        soup = findViewById(R.id.radioButtonSoupAddRecipe);
        main = findViewById(R.id.radioButtonMainAddRecipe);
        appetizer = findViewById(R.id.radioButtonAppetizerAddRecipe);
        salad = findViewById(R.id.radioButtonSaladAddRecipe);

        //radio buttons skill
        begginer = findViewById(R.id.begginer_radio_add_recipe);
        intermediate = findViewById(R.id.intermediate_radio_add_recipe);
        pro = findViewById(R.id.pro_radio_add_recipe);

        // skills radio group
        final RadioGroup skills = findViewById(R.id.skill_radio_group_add_recipe);

        // type radio group
        final RadioGroup type1 = findViewById(R.id.type_radio_group1_add_recipe);
        final RadioGroup type2 = findViewById(R.id.type_radio_group2_add_recipe);
        type1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && typeIsChecked) {
                    typeIsChecked = false;
                    type2.clearCheck();
                    typeCheckedId = checkedId;
                }
                typeIsChecked = true;
            }
        });

        type2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && typeIsChecked) {
                    typeIsChecked = false;
                    type1.clearCheck();
                    typeCheckedId = checkedId;
                }
                typeIsChecked = true;
            }
        });

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
                boolean legalRecipe = true;

                // mandatory fields
                if(title.getText().toString().isEmpty())
                    legalRecipe = false;
                if(recipe.getInstructions().size() == 0)
                    legalRecipe = false;
                if(recipe.getIngredients().size() == 0)
                    legalRecipe = false;

                // cuisine
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

                //set type
                if (typeIsChecked)
                    if(main.isChecked())
                        recipe.setType(Recipe.recipeType.MAIN);
                    else if(dessert.isChecked())
                        recipe.setType(Recipe.recipeType.DESSERT);
                    else if(salad.isChecked())
                        recipe.setType(Recipe.recipeType.SALAD);
                    else if(soup.isChecked())
                        recipe.setType(Recipe.recipeType.SOUP);
                    else if(appetizer.isChecked())
                        recipe.setType(Recipe.recipeType.APPETIZER);
                    else
                        recipe.setType(null);

                //set skill level
                if (begginer.isChecked())
                    recipe.setSkillLevel(UserProfile.skillLevel.BEGINNER);
                else if (intermediate.isChecked())
                    recipe.setSkillLevel(UserProfile.skillLevel.INTERMEDIATE);
                else if (pro.isChecked())
                    recipe.setSkillLevel(UserProfile.skillLevel.PRO);

                //set author to be current user
                MyApplication app = ((MyApplication)getApplicationContext());
                recipe.setAuthor(app.getUser().getEmail());

                //set release time for recipe
                recipe.setReleaseDate(new Date(System.currentTimeMillis()));

                //set recipe name
                recipe.setName(title.getText().toString().trim());

                // Set description
                recipe.setDescription(description.getText().toString());

                if (!legalRecipe){
                    progressDialog.dismiss();
                    Toast toast = Toast.makeText(AddRecipe.this, "Title, instructions and ingredients\n" +
                            "are mandatory fields", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                //add the recipe to the database
                try {
                    recipe = (Recipe) new DatabaseServiceTask("addRecipe", app).execute(recipe).get();
                    Toast.makeText(AddRecipe.this, "Recipe Added", Toast.LENGTH_SHORT);
                    app.setRecipe(recipe);
                    startActivity(new Intent(AddRecipe.this, RecipePage.class));
                }
                catch (ExecutionException | InterruptedException e) {
                    Toast toast = Toast.makeText(AddRecipe.this, "Failed to add new Recipe", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout_button: {
                finish();
                startActivity(new Intent(AddRecipe.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                app.setIsMyArea(true);
                startActivity(new Intent(AddRecipe.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                app.setHome(true);
                startActivity(new Intent(AddRecipe.this, HomePage.class));
                break;
            }
            case R.id.account_button: {
                startActivity(new Intent(AddRecipe.this, Settings.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
