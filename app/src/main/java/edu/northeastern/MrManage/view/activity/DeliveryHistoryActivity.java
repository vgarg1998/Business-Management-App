package edu.northeastern.MrManage.view.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.view_model.DeliveryViewModel;
import edu.northeastern.MrManage.view.adapters.ActiveOrderAdapter;
import edu.northeastern.MrManage.view.adapters.DeliveryAdapter;

@AndroidEntryPoint
public class DeliveryHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DeliveryAdapter adapter;

    DeliveryViewModel deliveryViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_history);

        recyclerView = findViewById(R.id.delivery_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        deliveryViewModel = new ViewModelProvider(this).get(DeliveryViewModel.class);
        deliveryViewModel.getAllDeliveries().observe(this,deliveries->{
            if (deliveries != null) {
                if (adapter == null) {
                    // Initialize adapter after receiving data
                    adapter = new DeliveryAdapter(deliveries, this);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Update existing adapter
                    adapter.updateDelivery(deliveries);
                }
            }
        });


    }
}
