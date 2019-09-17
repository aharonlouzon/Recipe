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

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyAdapterUsers extends RecyclerView.Adapter<MyHolderUsers> {

    private Context c;
    private ArrayList<String> models;
    private MyApplication app;

    MyAdapterUsers(Context c, ArrayList<String> models) {
        this.c = c;
        this.models = models;
        app = ((MyApplication) getApplicationContext());
    }

    @NonNull
    @Override
    public MyHolderUsers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, null);
        return new MyHolderUsers(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolderUsers holder, int position) {
        if (models.size() > 0) {
            UserProfile user = getUser(models.get(position));
            holder.setUser(user);
            if (user.getPicture() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPicture(), 0, user.getPicture().length);
                holder.setBitmap(bitmap);
                holder.imageView.setImageBitmap(bitmap);
            }
            if (app.getUser().getFollowers().contains(user.getEmail()))
                holder.button.setText("unfollow");
            else
                holder.button.setText("follow");
            if (user.getEmail().equals(app.getUser().getEmail()))
                holder.button.setVisibility(View.GONE);
            String name = user.getFirstName() + " " + user.getLastName();
            holder.userName.setText(name);
        }

        Animation animation = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }

    private UserProfile getUser(String userEmail) {
        Object response;
        try {
            response = new DatabaseServiceTask("getUser", app).execute(userEmail).get();

            if (!(response instanceof UserProfile))
                if (response instanceof Exception)
                    throw (Exception) response;

            UserProfile user = null;
            if (response instanceof UserProfile)
                user = (UserProfile) response;

            return user;
        } catch (Exception e) {
            return new UserProfile();
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
