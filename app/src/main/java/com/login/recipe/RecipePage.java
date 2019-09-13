package com.login.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RecipePage extends AppCompatActivity {
    private Recipe recipe;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = ((MyApplication)getApplicationContext());
        recipe = app.getRecipe();
        TextView description = findViewById(R.id.recipe_page_description);
        description.setText(recipe.getDescription());
        TextView title = findViewById(R.id.recipe_page_title);
        title.setText(recipe.getName());

        // author
        final TextView author = findViewById(R.id.recipe_page_author);
        author.setText(recipe.getAuthor());
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile userProfile = getAuthor(author.getText().toString());
                if (userProfile != null) {
                    app.setIsMyArea(false);
                    app.setVisitedUser(userProfile);
                    startActivity(new Intent(RecipePage.this, MyArea.class));
                }
            }
        });

        //image gridview
        RecyclerView recyclerView = findViewById(R.id.recipe_page_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (recipe.getImages().size() > 0) {
            MyAdapterRecipeImage myAdapter = new MyAdapterRecipeImage(this, recipe.getImages());
            recyclerView.setAdapter(myAdapter);
        }


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
        ArrayList<String> commentsText = new ArrayList<>();

        for (Map.Entry<String,String> entry : recipeIngredients.entrySet()) {
            recipeIngredientsArray.add(entry.getKey() + "   " +  entry.getValue());
        }

        for(Comment comment : recipe.getComments()) {
            commentsText.add(comment.getAuthorName() + "\n" + comment.getComment());
        }

        //locate Views
        ListView ingredientsListView = findViewById(R.id.ingredients_list_view);
        ListView instructionsListView = findViewById(R.id.instructions_list_view);
        ListView commentsList = findViewById(R.id.recipe_page_recipe_comments);


        //set all ListView adapters
        ingredientsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeIngredientsArray));
        instructionsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeInstructions));
        commentsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, commentsText));

        //set dynamic height for all ListViews
        setDynamicHeight(ingredientsListView);
        setDynamicHeight(instructionsListView);
        setDynamicHeight(commentsList);

        //handle comments
        ImageButton addCommentButton = findViewById(R.id.add_comment_button);
        final EditText commentText = findViewById(R.id.new_comment_text);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!commentText.getText().toString().isEmpty())
                    leaveComment(commentText.getText().toString());
            }
        });

    }

    @SuppressLint("ShowToast")
    public void leaveComment(String commentText){
        //create comment object
        String authorName = app.getUser().getFirstName() + " " + app.getUser().getLastName();
        String author = app.getUser().getEmail();
        Comment comment = new Comment(author, commentText, authorName);
        app.getRecipe().getComments().add(comment);

        //add the recipe comment to the database
        Recipe response = null;
        try {
            response = (Recipe) new DatabaseServiceTask("addComment", app).execute(recipe.getRecipeId(), comment).get();
            app.setRecipe(recipe);

            Toast toast = Toast.makeText(RecipePage.this, "Comment Added", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            startActivity(new Intent(RecipePage.this, RecipePage.class));
        }
        catch (ExecutionException | InterruptedException e) {
            Toast toast = Toast.makeText(RecipePage.this, "Failed to add comment", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
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

    @SuppressLint("ShowToast")
    public UserProfile getAuthor(String email){
        UserProfile userProfile = null;
        try {
            userProfile = (UserProfile) new DatabaseServiceTask("getUser", app).execute(email).get();
        }
        catch (ExecutionException | InterruptedException e) {
            Toast toast = Toast.makeText(RecipePage.this, "User no longer exist", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return userProfile;
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
                app.setIsMyArea(true);
                startActivity(new Intent(RecipePage.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                app.setHome(true);
                startActivity(new Intent(RecipePage.this, HomePage.class));
                break;
            }
            case R.id.account_button: {
                startActivity(new Intent(RecipePage.this, Settings.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
