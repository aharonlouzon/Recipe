package com.login.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class UsersList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        MyApplication app = ((MyApplication) getApplicationContext());
        String userListType = app.getUserListType();
        TextView header = findViewById(R.id.header_users_list);

        RecyclerView recyclerView = findViewById(R.id.users_list_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapterUsers myAdapter;
        if (userListType.equals("followers")) {
            header.setText("My followers:");
            myAdapter = new MyAdapterUsers(this, app.getUser().getFollowers());
        } else {
            header.setText("People I follow:");
            myAdapter = new MyAdapterUsers(this, app.getUser().getFollowerOf());
        }
        recyclerView.setAdapter(myAdapter);

    }

}
