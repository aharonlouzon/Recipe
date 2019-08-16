package com.login.recipe;

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

import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomePage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private MyApplication app;
    private UserProfile user;
    private ProgressDialog progressDialog;
    private RecipeList recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        RecyclerView recyclerView = findViewById(R.id.home_page_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(this, getRecipes());
        recyclerView.setAdapter(myAdapter);
        progressDialog = new ProgressDialog(this);
        app = ((MyApplication)getApplicationContext());
        user = app.getUser();


    }

    private RecipeList getRecipes(){

        //add user info to database
        try {
            progressDialog.setMessage("Cooking...");
            progressDialog.show();
            recipeList = (RecipeList) new DatabaseServiceTask("getUsersRecipes", app).execute(user.getEmail()).get();
        }
        catch (ExecutionException | InterruptedException e) {
            Toast.makeText(HomePage.this, "Failed to get user's recipes", Toast.LENGTH_SHORT);
        }

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
