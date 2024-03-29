package com.login.recipe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

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

    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseUser != null)
            userId = firebaseUser.getUid();

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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseUser != null)
            userId = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference(userId);

        addDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(direction_step.getText().toString().trim().isEmpty())
                    return;
                String step = direction_step.getText().toString();
                int step_num = Integer.parseInt(addDirection.getText().toString());
                step_num++;
                addDirection.setText(String.valueOf(step_num));
                recipe.setDirections(step);
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
                recipe.setIngredients(name, quan);
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
                if(recipe.getDirections().size() == 0)
                    return;
                if(recipe.getIngredients().size() == 0)
                    return;
                if(asian.isChecked())
                    recipe.setKitchenType("asian");
                if(middle_eastern.isChecked())
                    recipe.setKitchenType("middle_eastern");
                if(italian.isChecked())
                    recipe.setKitchenType("italian");
                if(european.isChecked())
                    recipe.setKitchenType("european");
                if(baking.isChecked())
                    recipe.setKitchenType("baking");
                if(meat.isChecked())
                    recipe.setKitchenType("meat");

                //generate unique id
                String uniqueID = UUID.randomUUID().toString();
                recipe.setRecipeId(uniqueID);

                //set author to be current user
                recipe.setAuthor(userId);

                //set release time for recipe
                recipe.setReleaseDate(System.currentTimeMillis());

                recipe.setName(title.getText().toString().trim());
                databaseReference.child("recipes").child(recipe.getRecipeId()).setValue(recipe);
                databaseReference.getRoot().child("recipe").child(recipe.getRecipeId()).setValue(recipe);

                startActivity(new Intent(AddRecipe.this, MyArea.class));
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
                startActivity(new Intent(AddRecipe.this, MainActivity.class));
            }
            case R.id.my_area_button_user_menu: {
                startActivity(new Intent(AddRecipe.this, MyArea.class));
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
