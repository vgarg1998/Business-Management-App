package edu.northeastern.MrManage.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.MrManage.R;

import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.dao.UserDao;
import edu.northeastern.MrManage.roomApi.entities.User;

import edu.northeastern.MrManage.view.adapters.ManageUserAdapter;

public class ManageUserActivity extends AppCompatActivity {
    ManageUserAdapter manageuserAdapter;
    RecyclerView recyclerView;


    MrManageDatabase mrManageDatabase;
    UserDao userDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_user);
        Intent intent = getIntent();
        boolean isCustomer = intent.getBooleanExtra("isCustomer", false);
        //this is where recycler view setup will come and call to get clients
        //Find recycler view
        mrManageDatabase = MrManageDatabase.getINSTANCE(getApplicationContext());
        userDao = mrManageDatabase.userDao();
        recyclerView = findViewById(R.id.manage_user_recycler_view);
        //Data fetch call right now test with dummy data
        //Start Async task


        //initialize adapter and give it the array list
        manageuserAdapter = new ManageUserAdapter(new ArrayList<>());
        //get data using view model

        new Thread(() -> {
            if(isCustomer){
                List<User> customers = userDao.getAllCustomers();
                runOnUiThread(() -> {
                    // Update the UI with the fetched data
                    manageuserAdapter.updateUsers(customers);
                });
            }else{
                List<User> users = userDao.getAllManufacturers();
                runOnUiThread(() -> {
                    // Update the UI with the fetched data
                    manageuserAdapter.updateUsers(users);
                });
            }


        }).start();
        //set layout manager for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(manageuserAdapter);
    }
}
