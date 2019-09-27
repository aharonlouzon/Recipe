package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class MyArea extends AppCompatActivity {

    private MyApplication app;
    private ProgressDialog progressDialog;
    private UserProfile user;
    private Button follow;
    private static final String preferences = "recipeAppPrefs";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_area);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        app = ((MyApplication) getApplicationContext());
        if (app.isMyArea())
            user = app.getUser();
        else
            user = app.getVisitedUser();

        if (user.getEmail().equals(app.getUser().getEmail()))
            app.setIsMyArea(true);

        // follow button
        follow = findViewById(R.id.follow_button);
        if (app.isMyArea())
            follow.setVisibility(View.GONE);
        else {
            if (app.getUser().getFollowers().contains(app.getVisitedUser().getEmail()))
                follow.setText("unfollow");
            else
                follow.setText("follow");

            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (follow.getText().toString().equals("follow")) {
                        app.getUser().follow(app.getUser().getEmail(), user.getEmail(), v.getContext(), app);
                        follow.setText("unfollow");
                    } else {
                        app.getUser().unFollow(app.getUser().getEmail(), user.getEmail(), v.getContext(), app);
                        follow.setText("follow");
                    }
                    finish();
                    startActivity(getIntent());
                }
            });
        }

        RecyclerView recyclerView = findViewById(R.id.my_area_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecipeList recipeList = getRecipes();
        if (recipeList.size() > 0) {
            MyAdapter myAdapter = new MyAdapter(this, recipeList);
            recyclerView.setAdapter(myAdapter);
        }

        ImageView avatar = findViewById(R.id.imageView_my_area);
        if (user.getPicture() != null) {
            byte[] imageByte = user.getPicture();
            @SuppressWarnings("deprecation")
            Drawable imageDrawable = new BitmapDrawable(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
            avatar.setImageDrawable(imageDrawable);
        } else
            avatar.setImageResource(R.drawable.male_avatar);

        if (app.isMyArea()) {
            avatar.setClickable(true);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyArea.this, UploadUserImage.class));
                }
            });
        } else
            avatar.setClickable(false);

        TextView name = findViewById(R.id.name_my_area);
        TextView cookingSkills = findViewById(R.id.cooking_skills_my_area);

        name.setText(user.getFirstName() + " " + user.getLastName());
        cookingSkills.setText("Skills: " + user.getCookingSkills());

        // floating button
        FloatingActionButton fab = findViewById(R.id.recipe_page_add_photo);
        if (!app.isMyArea()) {
            fab.hide();
        } else {
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MyArea.this, AddRecipe.class));
                }
            });
        }

        TextView followers = findViewById(R.id.followers_my_area);
        TextView following = findViewById(R.id.following_my_area);

        String followersStr = user.getFollowers().size() + " followers";
        String followingStr = user.getFollowerOf().size() + " following";

        followers.setText(followersStr);
        following.setText(followingStr);

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setUserListType("followers");
                app.setUserListResource(user);
                startActivity(new Intent(MyArea.this, UsersList.class));
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setUserListType("following");
                app.setUserListResource(user);
                startActivity(new Intent(MyArea.this, UsersList.class));
            }
        });
    }

    @SuppressLint("ShowToast")
    private RecipeList getRecipes() {
        progressDialog.setMessage("Cooking...");
        progressDialog.show();
        Object response;

        // get user's recipes
        try {
            response = new DatabaseServiceTask("getUsersRecipes", app).execute(user.getEmail()).get();

            if (!(response instanceof RecipeList))
                if (response instanceof Exception)
                    throw (Exception) response;

            if (response instanceof RecipeList)
                return  (RecipeList) response;
        } catch (Exception e) {
            Toast toast = Toast.makeText(MyArea.this, "Failed to get user's recipes", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } finally {
            progressDialog.dismiss();
        }
        return new RecipeList();
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
                startActivity(new Intent(MyArea.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                app.setIsMyArea(true);
                startActivity(new Intent(MyArea.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                app.setHome(true);
                startActivity(new Intent(MyArea.this, HomePage.class));
                break;
            }
            case R.id.account_button: {
                startActivity(new Intent(MyArea.this, Settings.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
