package com.login.recipe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadImage extends AppCompatActivity {

    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private MyApplication app;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        // Initialize Views
        Button btnChoose = findViewById(R.id.btnChoose);
        Button btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);
        app = ((MyApplication) getApplicationContext());
        progressDialog = new ProgressDialog(this);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Cooking...");
                progressDialog.show();
                if (app.getUploadImageType() == MyApplication.uploadImageTypes.RECIPE)
                    uploadRecipeImage();
                else
                    uploadUserImage();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("ShowToast")
    private void uploadRecipeImage() {
        if (filePath != null) {
            Recipe recipe = app.getRecipe();

            InputStream iStream;
            byte[] inputData = null;

            try {
                iStream = getContentResolver().openInputStream(filePath);
                inputData = getBytes(iStream);
            } catch (Exception e) {
                Toast.makeText(UploadImage.this, "Failed to get image", Toast.LENGTH_SHORT);
                startActivity(new Intent(UploadImage.this, RecipePage.class));
            }

            // add the recipe to the database
            Object response;
            try {
                response = new DatabaseServiceTask("addPicture", app).execute(recipe.getRecipeId(), inputData).get();

                if (!(response instanceof Recipe))
                    if (response instanceof Exception)
                        throw (Exception) response;


                Recipe responseRecipe = null;
                if (response instanceof Recipe)
                    responseRecipe = (Recipe) response;

                app.setRecipe(responseRecipe);
                Toast toast = Toast.makeText(UploadImage.this, "Picture was added to recipe", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                startActivity(new Intent(UploadImage.this, RecipePage.class));
            } catch (Exception e) {
                Toast toast = Toast.makeText(UploadImage.this, "Failed to upload picture", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                startActivity(new Intent(UploadImage.this, RecipePage.class));
            }
        }
    }

    @SuppressLint("ShowToast")
    private void uploadUserImage() {
        if (filePath != null) {
            InputStream iStream;
            byte[] inputData = null;

            try {
                iStream = getContentResolver().openInputStream(filePath);
                inputData = getBytes(iStream);
            } catch (Exception e) {
                Toast.makeText(UploadImage.this, "Failed to get image", Toast.LENGTH_SHORT);
                startActivity(new Intent(UploadImage.this, MyArea.class));
            }

            // add the recipe to the database
            Object response;
            try {
                response = new DatabaseServiceTask("changeProfilePic", app)
                        .execute(inputData, app.getUser().getEmail()).get();

                if (!(response instanceof UserProfile))
                    if (response instanceof Exception)
                        throw (Exception) response;

                UserProfile user = null;
                if (response instanceof UserProfile)
                    user = (UserProfile) response;

                app.setUser(user);

                Toast toast = Toast.makeText(UploadImage.this, "Picture was added to user", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                startActivity(new Intent(UploadImage.this, MyArea.class));
            } catch (Exception e) {
                Toast toast = Toast.makeText(UploadImage.this, "Failed to upload picture", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                startActivity(new Intent(UploadImage.this, MyArea.class));
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
