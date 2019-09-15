package com.login.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageShow extends AppCompatActivity {

    private MyApplication app;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        app = ((MyApplication) getApplicationContext());

        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(app.getImageShow());

    }
}
