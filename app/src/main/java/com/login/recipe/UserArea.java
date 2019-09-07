package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class UserArea extends AppCompatActivity {

    private MyApplication app;
    private ProgressDialog progressDialog;
    private UserProfile user;
    private RecipeList recipeList;
    private Button follow;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        app = ((MyApplication)getApplicationContext());

        // check if same as user
        if (app.getUser().getEmail().equals(app.getVisitedUser().getEmail()))
            startActivity(new Intent(UserArea.this, MyArea.class));

        user = app.getVisitedUser();

        // follow button
        follow = findViewById(R.id.follow_button);
        if (app.getUser().getFollowers().contains(app.getVisitedUser().getEmail()))
            follow.setText("unfollow");
        else
            follow.setText("follow");

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follow.getText().toString().equals("follow"))
                    follow(app.getUser().getEmail(), user.getEmail());
                else
                    unFollow(app.getUser().getEmail(), user.getEmail());
            }
        });

        RecyclerView recyclerView = findViewById(R.id.user_area_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getRecipes();
        if (recipeList != null) {
            MyAdapter myAdapter = new MyAdapter(this, recipeList);
            recyclerView.setAdapter(myAdapter);
        }

        ImageView avatar = findViewById(R.id.imageView_user_area);
        if (user.getPicture() != null){
            byte[] imageByte = user.getPicture();
            Drawable imageDrawable = new BitmapDrawable(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
            avatar.setImageDrawable(imageDrawable);
        }
        else
            avatar.setImageResource(R.drawable.male_avatar);

        TextView name = findViewById(R.id.name_user_area);
        TextView cookingSkills = findViewById(R.id.cooking_skills_user_area);

        name.setText(app.getUser().getFirstName() + " " + app.getUser().getLastName());
        cookingSkills.setText("Skills: " + app.getUser().getCookingSkills());

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
            Toast toast = Toast.makeText(UserArea.this, "Failed to get user's recipes", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void follow(String userEmail, String followEmail){
        progressDialog.setMessage("Cooking...");
        progressDialog.show();

        UserProfile response;
        // follow
        try {
            response = (UserProfile) new DatabaseServiceTask("addFollower", app).execute(userEmail, followEmail).get();
            app.setUser(response);
            follow.setText("unfollow");
            progressDialog.dismiss();
        }
        catch (ExecutionException | InterruptedException | ClassCastException e) {
            Toast toast = Toast.makeText(UserArea.this, "Failed to follow user", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    public void unFollow(String userEmail, String followEmail){
        progressDialog.setMessage("Cooking...");
        progressDialog.show();

        UserProfile response;
        // unfollow
        try {
            response = (UserProfile) new DatabaseServiceTask("deleteFollower", app).execute(userEmail, followEmail).get();
            app.setUser(response);
            follow.setText("follow");
            progressDialog.dismiss();
        }
        catch (ExecutionException | InterruptedException | ClassCastException e) {
            Toast toast = Toast.makeText(UserArea.this, "Failed to follow user", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
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
                startActivity(new Intent(UserArea.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                startActivity(new Intent(UserArea.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                startActivity(new Intent(UserArea.this, HomePage.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
