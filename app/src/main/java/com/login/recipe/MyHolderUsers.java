package com.login.recipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyHolderUsers extends RecyclerView.ViewHolder{

    private MyApplication app;
    ImageView imageView;
    TextView userName;
    public Button button;
    private Bitmap bitmap;
    private UserProfile user;

    MyHolderUsers(@NonNull View itemView) {
        super(itemView);
        app = ((MyApplication)getApplicationContext());
        this.imageView = itemView.findViewById(R.id.user_image);
        this.userName = itemView.findViewById(R.id.user_name);
        this.button =itemView.findViewById(R.id.follow_unfollow);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().toString().equals("follow")) {
                    app.getUser().follow(app.getUser().getEmail(), user.getEmail(), v.getContext(), app);
                    button.setText("unfollow");
                }
                else {
                    app.getUser().unFollow(app.getUser().getEmail(), user.getEmail(), v.getContext(), app);
                    button.setText("follow");
                }
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setVisitedUser(user);
                v.getContext().startActivity(new Intent(v.getContext(), UserArea.class));
            }
        });
    }

    void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    @SuppressWarnings("unused")
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
