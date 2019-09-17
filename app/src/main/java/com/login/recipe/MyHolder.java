package com.login.recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title, description;
    private MyApplication app;
    private int recipeId;
    private ProgressDialog progressDialog;
    private Recipe recipe;

    MyHolder(@NonNull View itemView) {
        super(itemView);
        progressDialog = new ProgressDialog(itemView.getContext());
        app = ((MyApplication) getApplicationContext());
        this.imageView = itemView.findViewById(R.id.card_image);
        this.title = itemView.findViewById(R.id.model_title);
        this.description = itemView.findViewById(R.id.model_card_detail);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cooking...");
                progressDialog.show();
                app.setRecipe(getRecipe());
                v.getContext().startActivity(new Intent(v.getContext(), RecipePage.class));
            }
        });
    }

    @SuppressWarnings("unused")
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    @SuppressWarnings("unused")
    public void setDescription(TextView description) {
        this.description = description;
    }

    private Recipe getRecipe() {

        // get recipe by id
        try {
            recipe = (Recipe) new DatabaseServiceTask("getRecipe", app).execute(this.recipeId).get();
        } catch (ExecutionException | InterruptedException ignored) {

        }

        return recipe;

    }

    void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
