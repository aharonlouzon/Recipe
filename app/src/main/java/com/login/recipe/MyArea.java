package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MyArea extends AppCompatActivity {

    private MyApplication app;
    private ProgressDialog progressDialog;
    private UserProfile user;
    private RecipeList recipeList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_area);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        app = ((MyApplication)getApplicationContext());
        user = app.getUser();
        RecyclerView recyclerView = findViewById(R.id.my_area_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getRecipes();
        if (recipeList != null) {
            MyAdapter myAdapter = new MyAdapter(this, recipeList);
            recyclerView.setAdapter(myAdapter);
        }

        ImageView avatar = findViewById(R.id.imageView_my_area);
        if (user.getPicture() != null){
            byte[] imageByte = user.getPicture();
            Drawable imageDrawable = new BitmapDrawable(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
            avatar.setImageDrawable(imageDrawable);
        }
        else
            avatar.setImageResource(R.drawable.male_avatar);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyArea.this, UploadUserImage.class));
            }
        });

        TextView name = findViewById(R.id.name_my_area);
        TextView cookingSkills = findViewById(R.id.cooking_skills_my_area);

        name.setText(app.getUser().getFirstName() + " " + app.getUser().getLastName());
        cookingSkills.setText("Skills: " + app.getUser().getCookingSkills());

        FloatingActionButton fab = findViewById(R.id.recipe_page_add_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyArea.this, AddRecipe.class));
            }
        });
    }

    @SuppressLint("ShowToast")
    private void getRecipes(){
        progressDialog.setMessage("Cooking...");
        progressDialog.show();

        // get user's recipes
        try {
            recipeList = (RecipeList) new DatabaseServiceTask("getUsersRecipes", app).execute(user.getEmail()).get();
            progressDialog.dismiss();
        }
        catch (ExecutionException | InterruptedException | ClassCastException e) {
            Toast.makeText(MyArea.this, "Failed to get user's recipes", Toast.LENGTH_SHORT);
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
        switch (item.getItemId()){

            case R.id.logout_button: {
                finish();
                startActivity(new Intent(MyArea.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                startActivity(new Intent(MyArea.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                startActivity(new Intent(MyArea.this, HomePage.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
