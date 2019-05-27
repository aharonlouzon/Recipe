package com.login.recipe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MyHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title, description;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.card_image);
        this.title = itemView.findViewById(R.id.model_title);
        this.description = itemView.findViewById(R.id.model_card_detail);
    }
}
