package com.login.recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class MyAdapterRecipeImage extends RecyclerView.Adapter<MyHolderRecipeImage> {

    private Context c;
    private ArrayList<byte[]> models;


    MyAdapterRecipeImage(Context c, ArrayList<byte[]> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolderRecipeImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_image, null);
        return new MyHolderRecipeImage(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderRecipeImage holder, int position) {
        if (models.size() > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(models.get(position), 0, models.get(position).length);
            holder.setBitmap(bitmap);
            holder.imageView.setImageBitmap(bitmap);
        }

        Animation animation = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
