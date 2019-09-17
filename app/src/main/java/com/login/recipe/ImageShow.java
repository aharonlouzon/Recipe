package com.login.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        MyApplication app = ((MyApplication) getApplicationContext());

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(app.getImageShow());

    }
}
