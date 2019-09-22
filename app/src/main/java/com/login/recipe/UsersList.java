package com.login.recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class UsersList extends AppCompatActivity {

    private static final String preferences = "recipeAppPrefs";
    private MyApplication app;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = ((MyApplication) getApplicationContext());
        setContentView(R.layout.activity_users_list);

        MyApplication app = ((MyApplication) getApplicationContext());
        String userListType = app.getUserListType();
        TextView header = findViewById(R.id.header_users_list);

        RecyclerView recyclerView = findViewById(R.id.users_list_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapterUsers myAdapter;
        if (userListType.equals("followers")) {
            header.setText("My followers:");
            myAdapter = new MyAdapterUsers(this, app.getUserListResource().getFollowers());
        } else {
            header.setText("People I follow:");
            myAdapter = new MyAdapterUsers(this, app.getUserListResource().getFollowerOf());
        }
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout_button: {
                SharedPreferences sharedpreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);
                sharedpreferences.edit().remove("Email").apply();
                sharedpreferences.edit().remove("Password").apply();
                app.log_out();
                finishAffinity();
                startActivity(new Intent(UsersList.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                app.setIsMyArea(true);
                startActivity(new Intent(UsersList.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                app.setHome(true);
                startActivity(new Intent(UsersList.this, HomePage.class));
                break;
            }
            case R.id.account_button: {
                startActivity(new Intent(UsersList.this, Settings.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
