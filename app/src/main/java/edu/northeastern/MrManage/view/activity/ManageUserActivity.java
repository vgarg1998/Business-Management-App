package edu.northeastern.MrManage.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.view.adapters.ManageUserAdapter;

@AndroidEntryPoint
public class ManageUserActivity extends AppCompatActivity {
    ManageUserAdapter manageuserAdapter;
    RecyclerView recyclerView;
    UserViewModel userViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_user);
        Intent intent = getIntent();
        boolean isCustomer = intent.getBooleanExtra("isCustomer", false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        recyclerView = findViewById(R.id.manage_user_recycler_view);
        //initialize adapter and give it the array list
        manageuserAdapter = new ManageUserAdapter(new ArrayList<>());
        //get data using view model
        if (isCustomer) {
            userViewModel.getAllCustomer().observe(this, customers -> {
                manageuserAdapter.updateUsers(customers);
            });
        } else {
            userViewModel.getAllManufacturers().observe(this, manufacturers -> {
                manageuserAdapter.updateUsers(manufacturers);
            });
        }
        //set layout manager for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(manageuserAdapter);
    }
}
