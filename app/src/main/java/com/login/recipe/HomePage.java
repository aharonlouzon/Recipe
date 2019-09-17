package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.FacebookSdk;

public class HomePage extends AppCompatActivity {

    private MyApplication app;
    private ProgressDialog progressDialog;
    private RecipeList recipeList;
    private static final String preferences = "recipeAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        app = ((MyApplication) getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.home_page_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getRecipes();
        if (recipeList != null) {
            MyAdapter myAdapter = new MyAdapter(this, recipeList);
            recyclerView.setAdapter(myAdapter);
        }

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, SearchPage.class));
            }
        });

    }

    @SuppressLint("ShowToast")
    private void getRecipes() {
        progressDialog.setMessage("Cooking...");
        progressDialog.show();
        Object response;

        // get user's recipes
        try {
            response = new DatabaseServiceTask("searchRecipes", app).execute(app.getSearchBySkills(),
                    app.getSearchByCuisine(), app.getSearchByType(), app.getSearchByEmail(), app.getSearchByFreeText())
                    .get();

            if (!(response instanceof RecipeList))
                if (response instanceof Exception)
                    throw (Exception) response;

            if (response instanceof RecipeList)
                recipeList = (RecipeList) response;
        } catch (Exception e) {
            Toast toast = Toast.makeText(HomePage.this, "Failed to get user's recipes", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            recipeList = new RecipeList();
        } finally {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout_button: {
                SharedPreferences sharedpreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);
                sharedpreferences.edit().remove("Email").apply();
                sharedpreferences.edit().remove("Password").apply();
                app.log_out();
                finishAffinity();
                startActivity(new Intent(HomePage.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                app.setIsMyArea(true);
                startActivity(new Intent(HomePage.this, MyArea.class));
                break;
            }
            case R.id.account_button: {
                startActivity(new Intent(HomePage.this, Settings.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
