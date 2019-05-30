package com.login.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.home_page_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        myAdapter = new MyAdapter(this, getPlayers());
        recyclerView.setAdapter(myAdapter);
    }

    private ArrayList<Model> getPlayers(){
        ArrayList<Model> models = new ArrayList<>();

        Model m1 = new Model();
        m1.setTitle("some title");
        m1.setDescription("some description");
        m1.setImg(R.drawable.pic1);
        models.add(m1);

        Model m2 = new Model();
        m2.setTitle("some title 2");
        m2.setDescription("some description 2");
        m2.setImg(R.drawable.pic2);
        models.add(m2);

        Model m3 = new Model();
        m3.setTitle("some title 2");
        m3.setDescription("some description 2");
        m3.setImg(R.drawable.pic3);
        models.add(m3);

        Model m4 = new Model();
        m4.setTitle("some title");
        m4.setDescription("some description");
        m4.setImg(R.drawable.pic4);
        models.add(m4);

        Model m5 = new Model();
        m5.setTitle("some title 2");
        m5.setDescription("some description 2");
        m5.setImg(R.drawable.pic5);
        models.add(m5);

        Model m6 = new Model();
        m6.setTitle("some title 2");
        m6.setDescription("some description 2");
        m6.setImg(R.drawable.pic6);
        models.add(m6);


        return models;
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
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
