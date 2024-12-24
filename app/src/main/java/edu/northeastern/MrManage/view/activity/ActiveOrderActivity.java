package edu.northeastern.MrManage.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.view_model.OrderViewModel;
import edu.northeastern.MrManage.view.adapters.ActiveOrderAdapter;
import edu.northeastern.MrManage.view.dialogs.ViewOrderDialog;


@AndroidEntryPoint
public class ActiveOrderActivity extends AppCompatActivity {

    // Initialize the list
    ActiveOrderAdapter activeOrderAdapter;
    RecyclerView activeOrderRecyclerView;

    OrderViewModel orderViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_order_layout);


        // Initialize RecyclerView and Adapter before adding data
        activeOrderRecyclerView = findViewById(R.id.active_order_recycler_view);

        activeOrderRecyclerView.setAdapter(activeOrderAdapter);
        activeOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data in a background thread
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        // Observe changes in LiveData from the ViewModel
        orderViewModel.getAllActiveOrders().observe(this, activeOrders -> {
            Log.d("ActiveOrders", "Observed orders: " + activeOrders);
            if (activeOrders != null) {
                if (activeOrderAdapter == null) {
                    // Initialize adapter after receiving data
                    activeOrderAdapter = new ActiveOrderAdapter(activeOrders, this);
                    activeOrderRecyclerView.setAdapter(activeOrderAdapter);
                } else {
                    // Update existing adapter
                    activeOrderAdapter.updateOrders(activeOrders);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            Order order = data.getParcelableExtra("order");
            String productName = data.getStringExtra("productName");
            String customerName = data.getStringExtra("customerName");
            int numberOfShipments = data.getIntExtra("numberOfShipments", -1);
            double receivedQuantity = data.getDoubleExtra("receivedQuantity", -1);
            int position = data.getIntExtra("position", -1);

            if (numberOfShipments == -1 || receivedQuantity == -1 || position == -1) {
                Toast.makeText(this, "Please re-open order details to view changes", Toast.LENGTH_SHORT).show();
            } else {
                order.setReceivedQuantity(receivedQuantity);
                activeOrderAdapter.updateOrder(position, order);
                new ViewOrderDialog().showDialog(this, order, productName, customerName, numberOfShipments, receivedQuantity, position);
            }


            // Fetch updated data from Room DB
        }
    }
}