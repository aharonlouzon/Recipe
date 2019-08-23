package com.login.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Map;

public class RecipePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        ArrayList<String> recipeIngredientsArray = new ArrayList<>();

        for (Map.Entry<String,String> entry : recipeIngredients.entrySet()) {
            recipeIngredientsArray.add(entry.getKey() + "   " +  entry.getValue());
        }

        //locate Views
        ListView ingredients_list_view = findViewById(R.id.ingredients_list_view);
        ListView instructions_list_view = findViewById(R.id.instructions_list_view);

        //set all Listview adapter
        ingredients_list_view.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeIngredientsArray));
        instructions_list_view.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeInstructions));

        //set dynmic height for all listviews
        setDynamicHeight(ingredients_list_view);
        setDynamicHeight(instructions_list_view);
    }

    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
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
                startActivity(new Intent(RecipePage.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                startActivity(new Intent(RecipePage.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                startActivity(new Intent(RecipePage.this, HomePage.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
