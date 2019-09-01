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
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import java.util.concurrent.ExecutionException;

public class HomePage extends AppCompatActivity {

    private MyApplication app;
    private UserProfile user;
    private ProgressDialog progressDialog;
    private RecipeList recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_home_page);

        progressDialog = new ProgressDialog(this);
        app = ((MyApplication)getApplicationContext());
        user = app.getUser();
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
    private RecipeList getRecipes(){
        progressDialog.setMessage("Cooking...");
        progressDialog.show();

        // get user's recipes
        try {
            recipeList = (RecipeList) new DatabaseServiceTask("getUsersRecipes", app).execute(user.getEmail()).get();
        }
        catch (ExecutionException | InterruptedException | ClassCastException e) {
            Toast.makeText(HomePage.this, "Failed to get user's recipes", Toast.LENGTH_SHORT);
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
//                app.log_out();
//                finishAffinity();
                startActivity(new Intent(HomePage.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                startActivity(new Intent(HomePage.this, MyArea.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
