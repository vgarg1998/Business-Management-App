package edu.northeastern.MrManage.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.view.adapters.ActiveOrderAdapter;
import edu.northeastern.MrManage.view.dialogs.ViewOrderDialog;

public class ActiveOrderActivity extends AppCompatActivity {

    List<Order> orders = new ArrayList<>(); // Initialize the list
    ActiveOrderAdapter activeOrderAdapter = new ActiveOrderAdapter(orders);
    RecyclerView activeOrderRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_order_layout);

        // Initialize RecyclerView and Adapter before adding data
        activeOrderRecyclerView = findViewById(R.id.active_order_recycler_view);

        activeOrderRecyclerView.setAdapter(activeOrderAdapter);
        activeOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data in a background thread
        new Thread(() -> {
            List<Order> fetchedOrders = MrManageDatabase.getINSTANCE(getApplicationContext()).orderDao().getActiveOrders();
            runOnUiThread(() -> {
                // Update the list and notify the adapter
                orders.clear();
                orders.addAll(fetchedOrders);
                activeOrderAdapter.notifyDataSetChanged(); // Notify adapter about data change
            });
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

             Order order = data.getParcelableExtra("order");
             String productName = data.getStringExtra("productName");
             String customerName = data.getStringExtra("customerName");
             int numberOfShipments = data.getIntExtra("numberOfShipments",-1);
             double receivedQuantity = data.getDoubleExtra("receivedQuantity",-1);
             int position = data.getIntExtra("position",-1);

             if(numberOfShipments==-1 || receivedQuantity == -1 || position == -1){
                 Toast.makeText(this, "Please re-open order details to view changes", Toast.LENGTH_SHORT).show();
             }else{
                 order.setReceivedQuantity(receivedQuantity);
                 activeOrderAdapter.updateOrder( position, order);
                 new ViewOrderDialog().showDialog(this, order, productName, customerName, numberOfShipments, receivedQuantity, position);
             }


            // Fetch updated data from Room DB
        }
    }
}