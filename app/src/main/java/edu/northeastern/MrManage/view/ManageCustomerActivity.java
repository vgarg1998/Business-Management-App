package edu.northeastern.MrManage.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.MrManage.R;

import edu.northeastern.MrManage.doa.api_call.CustomerViewModel;
import edu.northeastern.MrManage.view.adapters.ManageCustomerAdapter;

public class ManageCustomerActivity extends AppCompatActivity {
    ManageCustomerAdapter manageCustomerAdapter;
    RecyclerView recyclerView;
    CustomerViewModel customerViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_customers);
        //this is where recycler view setup will come and call to get clients
        //Find recycler view
        recyclerView = findViewById(R.id.manage_customer_recycler_view);
        //Data fetch call right now test with dummy data
        //Start Async task


        //initialize adapter and give it the array list
        manageCustomerAdapter = new ManageCustomerAdapter(new ArrayList<>());
        //get data using view model
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        customerViewModel.getCustomers().observe(this, customers ->{
            manageCustomerAdapter.updateClients(customers);
                }
                );
        customerViewModel.getErrorData().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });
        //set layout manager for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(manageCustomerAdapter);
    }
}
