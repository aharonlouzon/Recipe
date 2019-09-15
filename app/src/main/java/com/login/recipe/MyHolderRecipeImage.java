package com.login.recipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyHolderRecipeImage extends RecyclerView.ViewHolder {

    private MyApplication app;
    ImageView imageView;
    Bitmap bitmap;

    MyHolderRecipeImage(@NonNull View itemView) {
        super(itemView);
        app = ((MyApplication) getApplicationContext());
        this.imageView = itemView.findViewById(R.id.card_image_recipe);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setImageShow(bitmap);
                v.getContext().startActivity(new Intent(v.getContext(), ImageShow.class));
            }
        });
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @SuppressWarnings("unused")
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
