package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.login.recipe.Recipe.recipeType;
import com.login.recipe.UserProfile.skillLevel;
import java.util.concurrent.ExecutionException;

public class SearchResults extends AppCompatActivity {

    private String searchText;
    private String cuisine;
    private String authorEmail;
    private recipeType recipeType;
    private skillLevel skillLevel;
    private MyApplication app;
    private ProgressDialog progressDialog;
    private RecipeList recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        app = ((MyApplication)getApplicationContext());
        searchText = app.getFreeTextSearch();
        progressDialog = new ProgressDialog(this);

        RecyclerView recyclerView = findViewById(R.id.home_page_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(this, getRecipes());
        recyclerView.setAdapter(myAdapter);
    }

    @SuppressLint("ShowToast")
    private RecipeList getRecipes(){
        progressDialog.setMessage("Cooking...");
        progressDialog.show();

        // get user's recipes
        try {
            recipeList = (RecipeList) new DatabaseServiceTask("getUsersRecipes", app).execute(skillLevel, cuisine, recipeType, authorEmail).get();
        }
        catch (ExecutionException | InterruptedException e) {
            Toast.makeText(SearchResults.this, "Failed to get user's recipes", Toast.LENGTH_SHORT);
        }
        progressDialog.dismiss();
        return recipeList;
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
                startActivity(new Intent(SearchResults.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                startActivity(new Intent(SearchResults.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                startActivity(new Intent(SearchResults.this, HomePage.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
