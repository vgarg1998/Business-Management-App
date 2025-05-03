package edu.northeastern.MrManage.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.view_model.OrderViewModel;
import edu.northeastern.MrManage.view.adapters.ReceivedOrderAdapter;

@AndroidEntryPoint
public class ReceivedOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ReceivedOrderAdapter receivedOrderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.received_order_activity);

        recyclerView = findViewById(R.id.received_order_recycler_view);

        recyclerView.setAdapter(receivedOrderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        orderViewModel.getReceivedOrder().observe(this, receivedOrders -> {
            if (receivedOrders != null) {
                if (receivedOrderAdapter == null) {
                    // Initialize adapter after receiving data
                    View rootView = getWindow().getDecorView().getRootView();
                    receivedOrderAdapter = new ReceivedOrderAdapter(receivedOrders, this);
                    recyclerView.setAdapter(receivedOrderAdapter);
                } else {
                    // Update existing adapter
                    receivedOrderAdapter.updateOrders(receivedOrders);
                }
            }
        });

    }
}
